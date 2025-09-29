package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Course;

public interface ICourseService {
    Course createCourse(Course course);
    List<Course> getCoursesBySeasonId(Long seasonId);
    Optional<Course> getCourseById(Long courseId);
    List<Course> getAllCourses();
    List<Course> findByDepartmentId(Long departmentId);
    List<Course> findByTitleContaining(String title);
    List<Course> findByStatusOptional(Long departmentId);
    List<Course> findByStatusMandatory(Long departmentId);
    Optional<Course> deleteCourse(Long courseId);
}