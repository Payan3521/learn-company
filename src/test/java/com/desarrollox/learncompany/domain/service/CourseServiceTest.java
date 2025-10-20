package com.desarrollox.learncompany.domain.service;

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
        
        course.setId(1L);
        course.setDepartment(department);
        course.setSeason(season);
        course.setInstructor(instructor);
        course.setDuration(488);
    }

    @Test //200
    void createCourse_success(){

    }

    @Test //404
    void createCourse_DepartmentNotFound(){

    }

    @Test //404
    void createCourse_SeasonNotFound(){

    }

    @Test //404
    void createCourse_UserNotFound(){

    }

    @Test //409
    void createCourse_InvalidRole(){

    }

    @Test //422
    void createCourse_DurationInvalid(){

    }

    @Test //200
    void getCoursesBySeasonId_success(){

    }

    @Test //204
    void getCoursesBySeasonId_isEmpty(){

    }

    @Test //200
    void getCourseById_success(){

    }

    @Test //404
    void getCourseById_CourseNotFound(){

    }

    @Test //200
    void getAllCourses_success(){

    }

    @Test //204
    void getAllCourses_isEmpty(){

    }

    @Test //200
    void findByDepartmentId_success(){

    }

    @Test //204
    void findByDepartmentId_isEmpty(){

    }

    @Test //200
    void findByTitleContaining_success(){

    }

    @Test //204
    void findByTitleContaining_isEmpty(){

    }

    @Test //200
    void findByStatusOptional_success(){

    }

    @Test //204
    void findByStatusOptional_isEmpty(){

    }

    @Test //200
    void findByStatusMandatory_success(){

    }

    @Test //204
    void findByStatusMandatory_isEmpty(){

    }

    @Test //200
    void deleteCourse_success(){

    }

    @Test //404
    void deleteCourse_CourseNotFound(){

    }

}