package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Course;

public interface IRepositoryCourse {
    List<Course> findCoursesBySeasonId(Long seasonId);
    Optional<Course> findById(Long courseId);
    List<Course> findAll();
    List<Course> findCoursesByFilters(Long deparment, String name);
    List<Course> findByStatusOptional(Long departmentId);
    List<Course> findByStatusMandatory(Long departmentId);
    Optional<Course> delete(Long courseId);
}