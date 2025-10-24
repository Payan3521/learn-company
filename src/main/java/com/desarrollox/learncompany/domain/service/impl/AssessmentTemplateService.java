package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
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
public class AssessmentTemplateService implements IAssessmentTemplateService {

    private final IRepositoryAssessmentTemplate repositoryAssessmentTemplate;
    private final IRepositoryModule repositoryModule;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = true)
    @Override
    public List<AssessmentTemplate> getAssessmentsByModuleId(Long moduleId) {
        loggingService.logInfo("Obteniendo AssessmentTemplates para moduleId: {}", moduleId);
        try {
            if (!repositoryModule.existsById(moduleId)) {
                loggingService.logError("Módulo con ID {} no encontrado", moduleId);
                throw new ModuleNotFoundException(moduleId);
            }
            List<AssessmentTemplate> assessments = repositoryAssessmentTemplate.findAssessmentsByModuleId(moduleId);
            if (assessments.isEmpty()) {
                loggingService.logWarning("No se encontraron AssessmentTemplates para moduleId: {}", moduleId);
            } else {
                loggingService.logInfo("Se encontraron {} AssessmentTemplates para moduleId: {}", assessments.size(), moduleId);
            }
            return assessments;
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentTemplates para moduleId {}: {}", moduleId, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AssessmentTemplate> findById(Long id) {
        loggingService.logInfo("Obteniendo AssessmentTemplate con ID: {}", id);
        try {
            if (!repositoryAssessmentTemplate.existsById(id)) {
                loggingService.logError("AssessmentTemplate con ID {} no encontrado", id);
                throw new AssessmentTemplateNotFoundException(id);
            }
            Optional<AssessmentTemplate> assessmentTemplate = repositoryAssessmentTemplate.findById(id);
            loggingService.logInfo("AssessmentTemplate ID {} obtenido exitosamente", id);
            return assessmentTemplate;
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentTemplate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<FeedBack> getFeedbackById(Long id) {
        loggingService.logInfo("Obteniendo feedback para AssessmentTemplate con ID: {}", id);
        try {
            if(!repositoryAssessmentTemplate.existsById(id)){
                loggingService.logError("AssessmentTemplate con ID {} no encontrado", id);
                throw new AssessmentTemplateNotFoundException(id);
            }
            List<FeedBack> feedbackList = repositoryAssessmentTemplate.getFeedback(id);
            if (feedbackList.isEmpty()) {
                loggingService.logWarning("No se encontraron feedbacks para AssessmentTemplate ID: {}", id);
            } else {
                loggingService.logInfo("Se encontraron {} feedbacks para AssessmentTemplate ID: {}", feedbackList.size(), id);
            }
            return feedbackList;
        } catch (Exception e) {
            loggingService.logError("Error al obtener feedback para AssessmentTemplate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}