package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.service.ICourseService;

public class CourseService implements ICourseService {

    @Override
    public List<Course> getCoursesBySeasonId(Long seasonId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCoursesBySeasonId'");
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCourseById'");
    }

    @Override
    public List<Course> getAllCourses() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCourses'");
    }

    @Override
    public List<Course> findCoursesByFilters(Long deparment, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCoursesByFilters'");
    }

    @Override
    public List<Course> findByStatusOptional(Long departmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByStatusOptional'");
    }

    @Override
    public List<Course> findByStatusMandatory(Long departmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByStatusMandatory'");
    }

    @Override
    public Optional<Course> deleteCourse(Long courseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCourse'");
    }
    
}
