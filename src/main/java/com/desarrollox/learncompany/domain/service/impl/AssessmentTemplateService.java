package com.desarrollox.learncompany.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.exception.AssessmentTemplateNotFoundException;
import com.desarrollox.learncompany.domain.exception.ModuleNotFoundException;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;
import com.desarrollox.learncompany.domain.service.IAssessmentTemplateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentTemplateService implements IAssessmentTemplateService{

    private final IRepositoryAssessmentTemplate repositoryAssessmentTemplate;
    private final IRepositoryModule repositoryModule;

    @Transactional(readOnly = true)
    @Override
    public List<AssessmentTemplate> getAssessmentsByModuleId(Long moduleId) {
        if(!repositoryModule.existsById(moduleId)){
            throw new ModuleNotFoundException(moduleId);
        }
        return repositoryAssessmentTemplate.findAssessmentsByModuleId(moduleId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AssessmentTemplate> findById(Long id) {
        if(!repositoryAssessmentTemplate.existsById(id)){
            throw new AssessmentTemplateNotFoundException(id);
        }

        return repositoryAssessmentTemplate.findById(id);
    }
    

    @Override
    public List<FeedBack> getFeedbackById(Long id) {
        return repositoryAssessmentTemplate.getFeedback(id);
    }
}