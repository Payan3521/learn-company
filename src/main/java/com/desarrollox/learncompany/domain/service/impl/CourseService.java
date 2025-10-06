package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.DurationCourseInvalidException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.SeasonNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.service.ICourseService;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final IRepositoryCourse repositoryCourse;
    private final IRepositoryDepartment repositoryDepartment;
    private final IRepositorySeason repositorySeason;
    private final IRepositoryUser repositoryUser;

    @Override
    public Course createCourse(Course course) {
        if(!repositoryDepartment.existsById(course.getDepartment().getId())){
            throw new CourseNotFoundException(course.getDepartment().getId());
        }
        if(!repositorySeason.existsById(course.getSeason().getId())){
            throw new SeasonNotFoundException(course.getSeason().getId());
        }
        if(!repositoryUser.existsById(course.getInstructor().getId())){
            throw new UserNotFoundException(course.getInstructor().getId());
        }
        if(!repositoryUser.findById(course.getInstructor().getId()).get().isInstructor()){
            throw new InvalidRoleException("El usuario con ID " + course.getInstructor().getId() + " no tiene rol de INSTRUCTOR");
        }
        if(course.getDuration() > repositorySeason.findById(course.getSeason().getId()).get().getDuration()){
            throw new DurationCourseInvalidException();
        }

        course.setDepartment(repositoryDepartment.findById(course.getDepartment().getId()).get());
        course.setSeason(repositorySeason.findById(course.getSeason().getId()).get());
        course.setInstructor((Instructor)repositoryUser.findById(course.getInstructor().getId()).get());

        return repositoryCourse.save(course);
    }

    @Override
    public List<Course> getCoursesBySeasonId(Long seasonId) {
        return repositoryCourse.getCoursesBySeasonId(seasonId);
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {
        if(repositoryCourse.existsById(courseId)){
            return repositoryCourse.findById(courseId);
        }
        throw new CourseNotFoundException(courseId);
    }

    @Override
    public List<Course> getAllCourses() {
        return repositoryCourse.findAll();
    }

    @Override
    public List<Course> findByDepartmentId(Long departmentId) {
        return repositoryCourse.findByDepartmentId(departmentId);
    }

    @Override
    public List<Course> findByTitleContaining(String title) {
        return repositoryCourse.findByTitleContaining(title);
    }

    @Override
    public List<Course> findByStatusOptional(Long departmentId) {
        return repositoryCourse.findByStatusOptional(departmentId);
    }

    @Override
    public List<Course> findByStatusMandatory(Long departmentId) {
        return repositoryCourse.findByStatusMandatory(departmentId);
    }

    @Override
    public Optional<Course> deleteCourse(Long courseId) {
        return repositoryCourse.delete(courseId);
    }
    
}