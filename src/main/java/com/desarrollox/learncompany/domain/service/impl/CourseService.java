package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.service.ICourseService;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final IRepositoryCourse repositoryCourse;

    @Override
    public Course createCourse(Course course) {
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