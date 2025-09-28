package com.desarrollox.learncompany.domain.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentInstance;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.domain.service.IAssessmentInstanceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentInstanceService implements IAssessmentInstanceService{

    private final IRepositoryAssessmentInstance repositoryAssessmentInstance;

    @Override
    public AssessmentInstance createAssessmentInstance(AssessmentInstance instance) {
        return repositoryAssessmentInstance.createAssessmentInstance(instance);
    }

    @Override
    public Optional<AssessmentInstance> getAssessmentInstanceById(Long id) {
        return repositoryAssessmentInstance.getAssessmentInstanceById(id);
    }

    @Override
    public Double getGrade(Long id) {
        Optional<AssessmentInstance> instanceOpt = repositoryAssessmentInstance.getAssessmentInstanceById(id);
        if(instanceOpt.isPresent()){
            return instanceOpt.get().getGrade();
        }
        return null;
    }

    @Override
    public Optional<AssessmentInstance> assignGrade(Long id, Double grade) {
       Optional<AssessmentInstance> instanceOpt = repositoryAssessmentInstance.getAssessmentInstanceById(id);
        if(instanceOpt.isPresent()){
            return repositoryAssessmentInstance.assignGrade(id, grade);
        }
        return Optional.empty();
    }
    
}