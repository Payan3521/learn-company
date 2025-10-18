package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.domain.model.FeedBack;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;

@Repository
public interface JpaRepositoryAssessmentTemplate extends JpaRepository<AssessmentTemplateEntity, Long>{

    @Query(value = "SELECT * FROM assessment_templates WHERE module_id = :id", nativeQuery = true)
    List<AssessmentTemplateEntity> findAssessmentsByModuleId(Long id);

    @Query(value = """
        SELECT 
            q.id AS id,
            q.question AS question,
            q.correct_answer AS answer
        FROM questions q
        WHERE q.assessment_template_id = :assessmentTemplateId
    """, nativeQuery = true)
    List<FeedBack> getFeedback(@Param("assessmentTemplateId") Long assessmentTemplateId);
}