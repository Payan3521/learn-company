package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import lombok.RequiredArgsConstructor;
import com.desarrollox.learncompany.persistence.mapper.AssessmentTemplateMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryAssessmentTemplate;

@Component
@RequiredArgsConstructor
public class RepositoryAssessmentTemplate implements IRepositoryAssessmentTemplate{

    private final JpaRepositoryAssessmentTemplate jpaRepositoryAssessmentTemplate;
    private final AssessmentTemplateMapper assessmentTemplateMapper;

    @Override
    public List<AssessmentTemplate> findAssessmentsByModuleId(Long moduleId) {
        return jpaRepositoryAssessmentTemplate.findAssessmentsByModuleId(moduleId).stream().map(assessmentTemplateMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryAssessmentTemplate.existsById(id);
    }

    @Override
    public Optional<AssessmentTemplate> findById(Long id) {
        return jpaRepositoryAssessmentTemplate.findById(id).map(assessmentTemplateMapper::toDomain);
    }
    
}