package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;

@Repository
public interface JpaRepositoryModule extends JpaRepository<ModuleEntity, Long> {
    
    @Query("SELECT m FROM ModuleEntity m WHERE m.course.id = :courseId")
    List<ModuleEntity> findByCourseId(@Param("courseId") Long courseId);
}