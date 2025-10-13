package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.exception.AssessmentTemplateNotFoundException;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;
import com.desarrollox.learncompany.domain.service.IAssessmentTemplateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentTemplateService implements IAssessmentTemplateService{

    private final IRepositoryAssessmentTemplate repositoryAssessmentTemplate;

    @Override
    public List<AssessmentTemplate> getAssessmentsByModuleId(Long moduleId) {
        return repositoryAssessmentTemplate.findAssessmentsByModuleId(moduleId);
    }

    @Override
    public FeedBack getFeedbackById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFeedbackById'");
    }

    @Override
    public Optional<AssessmentTemplate> findById(Long id) {
        if(!repositoryAssessmentTemplate.existsById(id)){
            throw new AssessmentTemplateNotFoundException(id);
        }

        return repositoryAssessmentTemplate.findById(id);
    }
    
}