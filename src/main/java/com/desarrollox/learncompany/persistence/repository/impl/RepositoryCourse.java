package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.model.Course;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryCourse implements IRepositoryCourse{@Override
    public List<Course> findCoursesBySeasonId(Long seasonId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCoursesBySeasonId'");
    }

    @Override
    public Optional<Course> findById(Long courseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Course> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
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
    public Optional<Course> delete(Long courseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}