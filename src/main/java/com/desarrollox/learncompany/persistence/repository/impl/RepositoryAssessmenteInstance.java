package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentInstance;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.persistence.entity.AnswerEntity;
import com.desarrollox.learncompany.persistence.entity.AssessmentInstanceEntity;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;
import com.desarrollox.learncompany.persistence.entity.EmployeeEntity;
import com.desarrollox.learncompany.persistence.entity.QuestionEntity;
import com.desarrollox.learncompany.persistence.mapper.MapperAssessmentInstanceWithRelations;
import com.desarrollox.learncompany.persistence.mapper.QuestionMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryAssessmentInstance;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryUser;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryAssessmenteInstance implements IRepositoryAssessmentInstance {

    private final JpaRepositoryAssessmentInstance jpaRepositoryAssessmentInstance;
    private final JpaRepositoryAssessmentTemplate jpaRepositoryAssessmentTemplate;
    private final JpaRepositoryUser jpaRepositoryUser;
    private final QuestionMapper questionMapper;
    private final MapperAssessmentInstanceWithRelations mapperAssessmentInstanceWithRelations;

    @Override
    public AssessmentInstance createAssessmentInstance(AssessmentInstance instance) {
        // Crear la entidad AssessmentInstance
        AssessmentInstanceEntity entity = new AssessmentInstanceEntity();
        entity.setGrade(instance.getGrade());
        entity.setStatus(instance.getStatus());
        
        // Establecer AssessmentTemplate usando entidad existente
        if (instance.getAssessmentTemplate() != null) {
            AssessmentTemplateEntity assessmentTemplateEntity = jpaRepositoryAssessmentTemplate
                .findById(instance.getAssessmentTemplate().getId()).orElse(null);
            entity.setAssessmentTemplate(assessmentTemplateEntity);
        }
        
        // Establecer Employee usando entidad existente
        if (instance.getEmployee() != null) {
            EmployeeEntity employeeEntity = jpaRepositoryUser.findById(instance.getEmployee().getId())
                .filter(userEntity -> userEntity instanceof EmployeeEntity)
                .map(userEntity -> (EmployeeEntity) userEntity)
                .orElse(null);
            entity.setEmployee(employeeEntity);
        }
        
        // Mapear Answers manualmente siguiendo el patrón de RepositoryModule
        if (instance.getAnswers() != null) {
            entity.setAnswers(
                instance.getAnswers().stream()
                    .map(answer -> {
                        AnswerEntity answerEntity = new AnswerEntity();
                        answerEntity.setContent(answer.getContent());
                        answerEntity.setAssessmentInstance(entity);
                        
                        // Establecer Question usando entidad existente
                        if (answer.getQuestion() != null) {
                            QuestionEntity questionEntity = questionMapper.toEntity(answer.getQuestion());
                            answerEntity.setQuestion(questionEntity);
                        }
                        
                        return answerEntity;
                    })
                    .collect(Collectors.toList())
            );
        }
        
        AssessmentInstanceEntity savedEntity = jpaRepositoryAssessmentInstance.save(entity);
        return mapperAssessmentInstanceWithRelations.mapToDomainWithRelations(savedEntity);
    }

    @Override
    public Optional<AssessmentInstance> getAssessmentInstanceById(Long id) {
        return jpaRepositoryAssessmentInstance.findById(id)
                .map(mapperAssessmentInstanceWithRelations::mapToDomainWithRelations);
    }

    @Override
    public Optional<AssessmentInstance> assignGrade(Long id, Double grade) {
        return jpaRepositoryAssessmentInstance.findById(id)
                .map(entity -> {
                    entity.setGrade(grade);
                    return mapperAssessmentInstanceWithRelations.mapToDomainWithRelations(
                            jpaRepositoryAssessmentInstance.save(entity)
                    );
                });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryAssessmentInstance.existsById(id);
    }
    
}