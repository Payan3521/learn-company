package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Course;

public interface IRepositoryCourse {
    Course save(Course course);
    List<Course> getCoursesBySeasonId(Long seasonId);
    Optional<Course> findById(Long courseId);
    List<Course> findAll();
    List<Course> findByDepartmentId(Long departmentId);
    List<Course> findByTitleContaining(String title);
    List<Course> findByStatusOptional(Long departmentId);
    List<Course> findByStatusMandatory(Long departmentId);
    Optional<Course> delete(Long courseId);
    boolean existsById(Long id);
}