package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.CourseEntity;

@Repository
public interface JpaRepositoryCourse extends JpaRepository<CourseEntity, Long> {

    @Query(
        value = "SELECT * FROM courses c WHERE c.season_id = :seasonId", 
        nativeQuery = true
    )
    List<CourseEntity> findBySeasonId(@Param("seasonId")Long seasonId);

    @Query(
        value = "SELECT * FROM courses c WHERE c.department_id = :departmentId", 
        nativeQuery = true
    )
    List<CourseEntity> findByDepartmentId(Long departmentId);


    @Query(
        value = "SELECT * FROM courses c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))",
        nativeQuery = true
    )
    List<CourseEntity> findByTitleContaining(String title);

    @Query(
        value = "SELECT * FROM courses c WHERE c.department_id = :departmentId AND c.type_course = 'OPTIONAL'", 
        nativeQuery = true
    )
    List<CourseEntity> findByStatusOptional(Long departmentId);

    @Query(
        value = "SELECT * FROM courses c WHERE c.department_id = :departmentId AND c.type_course = 'MANDATORY'", 
        nativeQuery = true
    )
    List<CourseEntity> findByStatusMandatory(Long departmentId);
    
}