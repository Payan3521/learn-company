package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.Optional;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentInstance;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.persistence.mapper.AssessmentInstanceMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryAssessmentInstance;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RepositoryAssessmenteInstance implements IRepositoryAssessmentInstance {

    private final JpaRepositoryAssessmentInstance jpaRepositoryAssessmentInstance;
    private final AssessmentInstanceMapper assessmentInstanceMapper;

    @Override
    public AssessmentInstance createAssessmentInstance(AssessmentInstance instance) {
        return assessmentInstanceMapper.toDomain(
                jpaRepositoryAssessmentInstance.save(
                        assessmentInstanceMapper.toEntity(instance)
                )
        );
    }

    @Override
    public Optional<AssessmentInstance> getAssessmentInstanceById(Long id) {
        return jpaRepositoryAssessmentInstance.findById(id)
                .map(assessmentInstanceMapper::toDomain);
    }

    @Override
    public Optional<AssessmentInstance> assignGrade(Long id, Double grade) {
        return jpaRepositoryAssessmentInstance.findById(id)
                .map(entity -> {
                    entity.setGrade(grade);
                    return assessmentInstanceMapper.toDomain(
                            jpaRepositoryAssessmentInstance.save(entity)
                    );
                });
    }
    
}