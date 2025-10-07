package com.desarrollox.learncompany.domain.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentInstance;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.AssessmentTemplateNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.service.IAssessmentInstanceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentInstanceService implements IAssessmentInstanceService{

    private final IRepositoryAssessmentInstance repositoryAssessmentInstance;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryAssessmentTemplate repositoryAssessmentTemplate;

    @Override
    public AssessmentInstance createAssessmentInstance(AssessmentInstance instance) {
        if(!repositoryAssessmentTemplate.existsById(instance.getAssessmentTemplate().getId())){
            throw new AssessmentTemplateNotFoundException(instance.getAssessmentTemplate().getId());
        }
        if(!repositoryUser.existsById(instance.getEmployee().getId())){
            throw new UserNotFoundException(instance.getEmployee().getId());
        }
        if(!repositoryUser.findById(instance.getEmployee().getId()).get().isEmployee()){
            throw new InvalidRoleException("El usuario con ID: " + instance.getEmployee().getId() + "no tiene rol de EMPLOYEE");
        }

        instance.setAssessmentTemplate(repositoryAssessmentTemplate.findById(instance.getAssessmentTemplate().getId()).get());
        instance.setEmployee((Employee)repositoryUser.findById(instance.getEmployee().getId()).get());
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