package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Course;

public interface ICourseService {
    List<Course> getCoursesBySeasonId(Long seasonId);
    Optional<Course> getCourseById(Long courseId);
    List<Course> getAllCourses();
    List<Course> findCoursesByFilters(Long deparment, String name);
    List<Course> findByStatusOptional(Long departmentId);
    List<Course> findByStatusMandatory(Long departmentId);
    Optional<Course> deleteCourse(Long courseId);
}