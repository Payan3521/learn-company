package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.exception.DurationCourseInvalidException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.SeasonNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.CourseService;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    
    @Mock
    private IRepositoryCourse repositoryCourse;

    @Mock
    private IRepositoryDepartment repositoryDepartment;

    @Mock
    private IRepositorySeason repositorySeason;

    @Mock
    private IRepositoryUser repositoryUser;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private Season season;
    private Instructor instructor;
    private Department department;
    private List<Course> courseList;

    @BeforeEach
    void setUp(){
        department = new Department();
        department.setId(1L);

        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setDepartment(department);
        instructor.setRole(Role.INSTRUCTOR);
        
        season = new Season();
        season.setId(1L);
        season.setDuration(500);
        
        course = new Course();
        course.setId(1L);
        course.setDepartment(department);
        course.setSeason(season);
        course.setInstructor(instructor);
        course.setDuration(488);
        course.setTitle("java");

        courseList = new ArrayList<>();
        courseList.add(course);
    }

    @Test //200
    void createCourse_success(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositorySeason.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(instructor));
        when(repositorySeason.findById(anyLong())).thenReturn(Optional.of(season));
        when(repositoryDepartment.findById(anyLong())).thenReturn(Optional.of(department));
        when(repositoryCourse.save(any(Course.class))).thenReturn(course);

        Course result = courseService.createCourse(course);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositorySeason).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, times(2)).findById(anyLong());
        verify(repositorySeason, times(2)).findById(anyLong());
        verify(repositoryDepartment).findById(anyLong());
        verify(repositoryCourse).save(any(Course.class));
    }

    @Test //404
    void createCourse_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class,
            () -> courseService.createCourse(course)
        );

        assertNotNull(thrown);
        verify(repositoryDepartment).existsById(anyLong());
        verify(repositorySeason, never()).existsById(anyLong());
        verify(repositoryUser, never()).existsById(anyLong());
        verify(repositoryCourse, never()).save(any(Course.class));
    }

    @Test //404
    void createCourse_SeasonNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositorySeason.existsById(anyLong())).thenReturn(false);

        SeasonNotFoundException thrown = assertThrows(
            SeasonNotFoundException.class,
            () -> courseService.createCourse(course)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositorySeason).existsById(anyLong());
        verify(repositoryUser, never()).existsById(anyLong());
        verify(repositoryCourse, never()).save(any(Course.class));
    }

    @Test //404
    void createCourse_UserNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositorySeason.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> courseService.createCourse(course) 
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositorySeason).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryCourse, never()).save(any(Course.class));
    }

    @Test //409
    void createCourse_InvalidRole(){
        Instructor instructorLocal = new Instructor();
        instructorLocal.setId(2L);
        instructorLocal.setRole(Role.EMPLOYEE);

        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositorySeason.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(instructorLocal));

        InvalidRoleException thrown = assertThrows(
            InvalidRoleException.class,
            () -> courseService.createCourse(course)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositorySeason).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryCourse, never()).save(any(Course.class));
    }

    @Test //422
    void createCourse_DurationCourseInvalid(){
        Course courseLocal = new Course();
        courseLocal.setDepartment(department);
        courseLocal.setSeason(season);
        courseLocal.setInstructor(instructor);
        courseLocal.setId(2L);
        courseLocal.setDuration(550);

        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositorySeason.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(instructor));
        when(repositorySeason.findById(anyLong())).thenReturn(Optional.of(season));
        DurationCourseInvalidException thrown = assertThrows(
            DurationCourseInvalidException.class, 
            () -> courseService.createCourse(courseLocal)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositorySeason).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositorySeason).findById(anyLong());
        verify(repositoryCourse, never()).save(any(Course.class));

    }

    @Test //200
    void getCoursesBySeasonId_success(){
        when(repositorySeason.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.getCoursesBySeasonId(anyLong())).thenReturn(courseList);

        List<Course> result = courseService.getCoursesBySeasonId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(courseList, result);

        verify(repositorySeason).existsById(anyLong());
        verify(repositoryCourse).getCoursesBySeasonId(anyLong());
    }

    @Test //204
    void getCoursesBySeasonId_isEmpty(){
        when(repositorySeason.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.getCoursesBySeasonId(anyLong())).thenReturn(new ArrayList<>());

        List<Course> result = courseService.getCoursesBySeasonId(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(courseList, result);

        verify(repositorySeason).existsById(anyLong());
        verify(repositoryCourse).getCoursesBySeasonId(anyLong());
    }

    @Test //404
    void getCourseBySeasonId_SeasonNotFound(){
        when(repositorySeason.existsById(anyLong())).thenReturn(false);

        SeasonNotFoundException thrown = assertThrows(
            SeasonNotFoundException.class,
            () -> courseService.getCoursesBySeasonId(1L)
        );

        assertNotNull(thrown);

        verify(repositorySeason).existsById(anyLong());
        verify(repositoryCourse, never()).getCoursesBySeasonId(anyLong());
    }

    @Test //200
    void getCourseById_success(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findById(anyLong())).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getCourseById(1L);

        assertTrue(result.isPresent());
        assertEquals(course, result.get());
        assertEquals(course.getId(), result.get().getId());

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse).findById(anyLong());
    }

    @Test //404
    void getCourseById_CourseNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> courseService.getCourseById(1l)
        );

        assertNotNull(thrown);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse, never()).findById(anyLong());
    }

    @Test //200
    void getAllCourses_success(){
        when(repositoryCourse.findAll()).thenReturn(courseList);

        List<Course> result = courseService.getAllCourses();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(courseList, result);

        verify(repositoryCourse).findAll();
    }

    @Test //204
    void getAllCourses_isEmpty(){
        when(repositoryCourse.findAll()).thenReturn(new ArrayList<>());

        List<Course> result = courseService.getAllCourses();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(courseList, result);

        verify(repositoryCourse).findAll();
    }

    @Test //200
    void findByDepartmentId_success(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findByDepartmentId(anyLong())).thenReturn(courseList);

        List<Course> result = courseService.findByDepartmentId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(courseList, result);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse).findByDepartmentId(anyLong());
    }

    @Test //204
    void findByDepartmentId_isEmpty(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findByDepartmentId(anyLong())).thenReturn(new ArrayList<>());

        List<Course> result = courseService.findByDepartmentId(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(courseList, result);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse).findByDepartmentId(anyLong());
    }

    @Test //404
    void findByDepartmentId_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class,
            () -> courseService.findByDepartmentId(1L)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse, never()).findByDepartmentId(anyLong());
    }

    @Test //200
    void findByTitleContaining_success(){
        when(repositoryCourse.findByTitleContaining(anyString())).thenReturn(courseList);

        List<Course> result = courseService.findByTitleContaining("java");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("java", result.get(0).getTitle());
        assertEquals(courseList, result);

        verify(repositoryCourse).findByTitleContaining(anyString());
    }

    @Test //204
    void findByTitleContaining_isEmpty(){
        when(repositoryCourse.findByTitleContaining(anyString())).thenReturn(new ArrayList<>());

        List<Course> result = courseService.findByTitleContaining("java");

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(courseList, result);

        verify(repositoryCourse).findByTitleContaining(anyString());
    }

    @Test //200
    void findByStatusOptional_success(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findByStatusOptional(anyLong())).thenReturn(courseList);

        List<Course> result = courseService.findByStatusOptional(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(courseList, result);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse).findByStatusOptional(anyLong());
    }

    @Test //204
    void findByStatusOptional_isEmpty(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findByStatusOptional(anyLong())).thenReturn(new ArrayList<>());

        List<Course> result = courseService.findByStatusOptional(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(courseList, result);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse).findByStatusOptional(anyLong());
    }

    @Test //404
    void findByStatusOptional_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class,
            () -> courseService.findByStatusOptional(1L)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse, never()).findByStatusOptional(anyLong());
    }

    @Test //200
    void findByStatusMandatory_success(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findByStatusMandatory(anyLong())).thenReturn(courseList);

        List<Course> result = courseService.findByStatusMandatory(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(courseList, result);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse).findByStatusMandatory(anyLong());
    }

    @Test //204
    void findByStatusMandatory_isEmpty(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findByStatusMandatory(anyLong())).thenReturn(new ArrayList<>());

        List<Course> result = courseService.findByStatusMandatory(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(courseList, result);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse).findByStatusMandatory(anyLong());
    }

    @Test //404
    void findByStatusMandatory_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class,
            () -> courseService.findByStatusMandatory(1L)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryCourse, never()).findByStatusMandatory(anyLong());
    }

    @Test //200
    void deleteCourse_success(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.delete(anyLong())).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.deleteCourse(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse).delete(anyLong());
    }

    @Test //404
    void deleteCourse_CourseNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> courseService.deleteCourse(1L)
        );

        assertNotNull(thrown);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse, never()).delete(anyLong());
    }

}