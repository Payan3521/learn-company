package com.desarrollox.learncompany.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.AssessmentEntity;

@Repository
public interface JpaRepositoryAssessment extends JpaRepository<AssessmentEntity, Long> {
    
}