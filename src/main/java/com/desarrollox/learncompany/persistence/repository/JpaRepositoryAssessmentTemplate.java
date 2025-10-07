package com.desarrollox.learncompany.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;

@Repository
public interface JpaRepositoryAssessmentTemplate extends JpaRepository<AssessmentTemplateEntity, Long>{
    
}