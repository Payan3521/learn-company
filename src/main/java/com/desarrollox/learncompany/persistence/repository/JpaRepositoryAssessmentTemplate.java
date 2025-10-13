package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;

@Repository
public interface JpaRepositoryAssessmentTemplate extends JpaRepository<AssessmentTemplateEntity, Long>{

    @Query(value = "SELECT * FROM assessment_templates WHERE module_id = :id", nativeQuery = true)
    List<AssessmentTemplateEntity> findAssessmentsByModuleId(Long id);
}