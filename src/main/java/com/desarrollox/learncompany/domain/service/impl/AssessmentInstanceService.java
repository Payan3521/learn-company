package com.desarrollox.learncompany.domain.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentInstance;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.AssessmentInstanceNotFoundException;
import com.desarrollox.learncompany.domain.exception.AssessmentTemplateNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Answer;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.service.IAssessmentInstanceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentInstanceService implements IAssessmentInstanceService {

    private final IRepositoryAssessmentInstance repositoryAssessmentInstance;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryAssessmentTemplate repositoryAssessmentTemplate;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public AssessmentInstance createAssessmentInstance(AssessmentInstance instance) {
        loggingService.logInfo("Iniciando creación de AssessmentInstance para employeeId: {} y assessmentTemplateId: {}", 
                instance.getEmployee().getId(), instance.getAssessmentTemplate().getId());

        if (!repositoryAssessmentTemplate.existsById(instance.getAssessmentTemplate().getId())) {
            loggingService.logError("AssessmentTemplate con ID {} no encontrado", instance.getAssessmentTemplate().getId());
            throw new AssessmentTemplateNotFoundException(instance.getAssessmentTemplate().getId());
        }
        if (!repositoryUser.existsById(instance.getEmployee().getId())) {
            loggingService.logError("Usuario con ID {} no encontrado", instance.getEmployee().getId());
            throw new UserNotFoundException(instance.getEmployee().getId());
        }
        if (!repositoryUser.findById(instance.getEmployee().getId()).get().isEmployee()) {
            loggingService.logError("El usuario con ID {} no tiene rol EMPLOYEE", instance.getEmployee().getId());
            throw new InvalidRoleException("El usuario con ID: " + instance.getEmployee().getId() + " no tiene rol de EMPLOYEE");
        }

        // Obtener las entidades completas de la base de datos
        AssessmentTemplate assessmentTemplate = repositoryAssessmentTemplate.findById(instance.getAssessmentTemplate().getId()).get();
        Employee employee = (Employee) repositoryUser.findById(instance.getEmployee().getId()).get();
        
        loggingService.logDebug("Entidades obtenidas: AssessmentTemplate ID={}, Employee ID={}", 
                assessmentTemplate.getId(), employee.getId());

        // Establecer las entidades en la instancia
        instance.setAssessmentTemplate(assessmentTemplate);
        instance.setEmployee(employee);

        if (instance.getAnswers() != null) {
            loggingService.logDebug("Procesando {} respuestas para AssessmentInstance", instance.getAnswers().size());
            for (Answer answer : instance.getAnswers()) {
                answer.setAssessmentInstance(instance);
            }
        }
        
        AssessmentInstance savedInstance = repositoryAssessmentInstance.createAssessmentInstance(instance);
        loggingService.logInfo("AssessmentInstance creado exitosamente con ID: {}", savedInstance.getId());
        return savedInstance;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AssessmentInstance> getAssessmentInstanceById(Long id) {
        loggingService.logInfo("Obteniendo AssessmentInstance con ID: {}", id);
        if (!repositoryAssessmentInstance.existsById(id)) {
            loggingService.logError("AssessmentInstance con ID {} no encontrado", id);
            throw new AssessmentInstanceNotFoundException(id);
        }
        Optional<AssessmentInstance> instance = repositoryAssessmentInstance.getAssessmentInstanceById(id);
        loggingService.logInfo("AssessmentInstance ID {} obtenido exitosamente", id);
        return instance;
    }

    @Transactional(readOnly = true)
    @Override
    public Double getGrade(Long id) {
        loggingService.logInfo("Obteniendo nota para AssessmentInstance con ID: {}", id);
        Optional<AssessmentInstance> instanceOpt = repositoryAssessmentInstance.getAssessmentInstanceById(id);
        if (instanceOpt.isPresent()) {
            Double grade = instanceOpt.get().getGrade();
            loggingService.logInfo("Nota obtenida para AssessmentInstance ID {}: {}", id, grade);
            return grade;
        }
        loggingService.logError("AssessmentInstance con ID {} no encontrado", id);
        throw new AssessmentInstanceNotFoundException(id);
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<AssessmentInstance> assignGrade(Long id, Double grade) {
        loggingService.logInfo("Asignando nota {} a AssessmentInstance con ID: {}", grade, id);
        Optional<AssessmentInstance> instanceOpt = repositoryAssessmentInstance.getAssessmentInstanceById(id);
        if (instanceOpt.isPresent()) {
            Optional<AssessmentInstance> updatedInstance = repositoryAssessmentInstance.assignGrade(id, grade);
            loggingService.logInfo("Nota asignada exitosamente a AssessmentInstance ID: {}", id);
            return updatedInstance;
        }
        loggingService.logError("AssessmentInstance con ID {} no encontrado", id);
        throw new AssessmentInstanceNotFoundException(id);
    }
}