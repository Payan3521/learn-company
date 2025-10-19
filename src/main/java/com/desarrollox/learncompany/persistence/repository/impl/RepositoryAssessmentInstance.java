package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
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
public class RepositoryAssessmentInstance implements IRepositoryAssessmentInstance {

    private final JpaRepositoryAssessmentInstance jpaRepositoryAssessmentInstance;
    private final JpaRepositoryAssessmentTemplate jpaRepositoryAssessmentTemplate;
    private final JpaRepositoryUser jpaRepositoryUser;
    private final QuestionMapper questionMapper;
    private final MapperAssessmentInstanceWithRelations mapperAssessmentInstanceWithRelations;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public AssessmentInstance createAssessmentInstance(AssessmentInstance instance) {
        loggingService.logInfo("Iniciando creación de AssessmentInstance para employeeId: {} y assessmentTemplateId: {}", 
                instance.getEmployee() != null ? instance.getEmployee().getId() : null, 
                instance.getAssessmentTemplate() != null ? instance.getAssessmentTemplate().getId() : null);
        try {
            AssessmentInstanceEntity entity = new AssessmentInstanceEntity();
            entity.setGrade(instance.getGrade());
            entity.setStatus(instance.getStatus());

            // Establecer AssessmentTemplate usando entidad existente
            if (instance.getAssessmentTemplate() != null) {
                loggingService.logDebug("Obteniendo AssessmentTemplate con ID: {}", instance.getAssessmentTemplate().getId());
                AssessmentTemplateEntity assessmentTemplateEntity = jpaRepositoryAssessmentTemplate
                        .findById(instance.getAssessmentTemplate().getId())
                        .orElseThrow(() -> {
                            loggingService.logError("AssessmentTemplate con ID {} no encontrado", instance.getAssessmentTemplate().getId());
                            return new IllegalArgumentException("AssessmentTemplate no encontrado");
                        });
                entity.setAssessmentTemplate(assessmentTemplateEntity);
            } else {
                loggingService.logWarning("No se proporcionó AssessmentTemplate para la AssessmentInstance");
            }

            // Establecer Employee usando entidad existente
            if (instance.getEmployee() != null) {
                loggingService.logDebug("Obteniendo Employee con ID: {}", instance.getEmployee().getId());
                EmployeeEntity employeeEntity = jpaRepositoryUser.findById(instance.getEmployee().getId())
                        .filter(userEntity -> userEntity instanceof EmployeeEntity)
                        .map(userEntity -> (EmployeeEntity) userEntity)
                        .orElseThrow(() -> {
                            loggingService.logError("Employee con ID {} no encontrado o no es un Employee", instance.getEmployee().getId());
                            return new IllegalArgumentException("Employee no encontrado o no válido");
                        });
                entity.setEmployee(employeeEntity);
            } else {
                loggingService.logWarning("No se proporcionó Employee para la AssessmentInstance");
            }

            // Mapear Answers manualmente
            if (instance.getAnswers() != null) {
                loggingService.logDebug("Procesando {} Answers para la AssessmentInstance", instance.getAnswers().size());
                entity.setAnswers(
                        instance.getAnswers().stream()
                                .map(answer -> {
                                    AnswerEntity answerEntity = new AnswerEntity();
                                    answerEntity.setContent(answer.getContent());
                                    answerEntity.setAssessmentInstance(entity);

                                    if (answer.getQuestion() != null) {
                                        loggingService.logDebug("Mapeando Question para Answer con ID: {}", answer.getQuestion().getId());
                                        QuestionEntity questionEntity = questionMapper.toEntity(answer.getQuestion());
                                        answerEntity.setQuestion(questionEntity);
                                    } else {
                                        loggingService.logWarning("No se proporcionó Question para un Answer");
                                    }

                                    return answerEntity;
                                })
                                .collect(Collectors.toList())
                );
            } else {
                loggingService.logDebug("No se proporcionaron Answers para la AssessmentInstance");
            }

            AssessmentInstanceEntity savedEntity = jpaRepositoryAssessmentInstance.save(entity);
            AssessmentInstance savedInstance = mapperAssessmentInstanceWithRelations.mapToDomainWithRelations(savedEntity);
            loggingService.logInfo("AssessmentInstance creada exitosamente con ID: {}", savedInstance.getId());
            return savedInstance;
        } catch (Exception e) {
            loggingService.logError("Error al crear AssessmentInstance para employeeId: {} y assessmentTemplateId: {}: {}", 
                    instance.getEmployee() != null ? instance.getEmployee().getId() : null, 
                    instance.getAssessmentTemplate() != null ? instance.getAssessmentTemplate().getId() : null, 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<AssessmentInstance> getAssessmentInstanceById(Long id) {
        loggingService.logInfo("Obteniendo AssessmentInstance con ID: {}", id);
        try {
            Optional<AssessmentInstance> instance = jpaRepositoryAssessmentInstance.findById(id)
                    .map(mapperAssessmentInstanceWithRelations::mapToDomainWithRelations);
            if (instance.isPresent()) {
                loggingService.logInfo("AssessmentInstance ID {} obtenida exitosamente", id);
            } else {
                loggingService.logWarning("AssessmentInstance con ID {} no encontrada", id);
            }
            return instance;
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentInstance ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<AssessmentInstance> assignGrade(Long id, Double grade) {
        loggingService.logInfo("Iniciando asignación de calificación {} a AssessmentInstance con ID: {}", grade, id);
        try {
            Optional<AssessmentInstanceEntity> entityOpt = jpaRepositoryAssessmentInstance.findById(id);
            if (entityOpt.isEmpty()) {
                loggingService.logError("AssessmentInstance con ID {} no encontrada", id);
                throw new IllegalArgumentException("AssessmentInstance no encontrada");
            }

            AssessmentInstanceEntity entity = entityOpt.get();
            entity.setGrade(grade);
            AssessmentInstanceEntity updatedEntity = jpaRepositoryAssessmentInstance.save(entity);
            AssessmentInstance updatedInstance = mapperAssessmentInstanceWithRelations.mapToDomainWithRelations(updatedEntity);
            loggingService.logInfo("Calificación {} asignada exitosamente a AssessmentInstance ID: {}", grade, id);
            return Optional.of(updatedInstance);
        } catch (Exception e) {
            loggingService.logError("Error al asignar calificación a AssessmentInstance ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de AssessmentInstance con ID: {}", id);
        try {
            boolean exists = jpaRepositoryAssessmentInstance.existsById(id);
            loggingService.logDebug("AssessmentInstance con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de AssessmentInstance ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}