package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.persistence.mapper.CourseMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryCourse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryCourse implements IRepositoryCourse{

    private final JpaRepositoryCourse jpaRepositoryCourse;
    private final CourseMapper courseMapper;
    
    @Override
    public Course save(Course course) {
        return courseMapper.toDomain(
            jpaRepositoryCourse.save(courseMapper.toEntity(course))
        );
    }

    @Override
    public List<Course> getCoursesBySeasonId(Long seasonId) {
        return jpaRepositoryCourse.findBySeasonId(seasonId).stream().map(courseMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findById(Long courseId) {
        return jpaRepositoryCourse.findById(courseId).map(courseMapper::toDomain);
    }

    @Override
    public List<Course> findAll() {
        return jpaRepositoryCourse.findAll().stream().map(courseMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Course> findByDepartmentId(Long departmentId) {
        return jpaRepositoryCourse.findByDepartmentId(departmentId).stream().map(courseMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Course> findByTitleContaining(String title) {
        return jpaRepositoryCourse.findByTitleContaining(title).stream().map(courseMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Course> findByStatusOptional(Long departmentId) {
        return jpaRepositoryCourse.findByStatusOptional(departmentId).stream().map(courseMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Course> findByStatusMandatory(Long departmentId) {
        return jpaRepositoryCourse.findByStatusMandatory(departmentId).stream().map(courseMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Course> delete(Long courseId) {
        return jpaRepositoryCourse.findById(courseId)
                .map(entity -> {
                    jpaRepositoryCourse.delete(entity);
                    return courseMapper.toDomain(entity);
                });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryCourse.existsById(id);
    }
    
}