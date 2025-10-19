package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;
import lombok.RequiredArgsConstructor;
import com.desarrollox.learncompany.persistence.mapper.AssessmentTemplateMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryAssessmentTemplate;

@Component
@RequiredArgsConstructor
public class RepositoryAssessmentTemplate implements IRepositoryAssessmentTemplate {

    private final JpaRepositoryAssessmentTemplate jpaRepositoryAssessmentTemplate;
    private final AssessmentTemplateMapper assessmentTemplateMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public List<AssessmentTemplate> findAssessmentsByModuleId(Long moduleId) {
        loggingService.logInfo("Obteniendo AssessmentTemplates para moduleId: {}", moduleId);
        try {
            List<AssessmentTemplate> templates = jpaRepositoryAssessmentTemplate.findAssessmentsByModuleId(moduleId)
                    .stream()
                    .map(assessmentTemplateMapper::toDomain)
                    .collect(Collectors.toList());
            if (templates.isEmpty()) {
                loggingService.logWarning("No se encontraron AssessmentTemplates para moduleId: {}", moduleId);
            } else {
                loggingService.logInfo("Se encontraron {} AssessmentTemplates para moduleId: {}", templates.size(), moduleId);
            }
            return templates;
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentTemplates para moduleId {}: {}", moduleId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de AssessmentTemplate con ID: {}", id);
        try {
            boolean exists = jpaRepositoryAssessmentTemplate.existsById(id);
            loggingService.logDebug("AssessmentTemplate con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de AssessmentTemplate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<AssessmentTemplate> findById(Long id) {
        loggingService.logInfo("Obteniendo AssessmentTemplate con ID: {}", id);
        try {
            Optional<AssessmentTemplate> template = jpaRepositoryAssessmentTemplate.findById(id)
                    .map(assessmentTemplateMapper::toDomain);
            if (template.isPresent()) {
                loggingService.logInfo("AssessmentTemplate ID {} obtenido exitosamente", id);
            } else {
                loggingService.logWarning("AssessmentTemplate con ID {} no encontrado", id);
            }
            return template;
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentTemplate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<FeedBack> getFeedback(Long id) {
        loggingService.logInfo("Obteniendo FeedBack para AssessmentTemplate con ID: {}", id);
        try {
            List<FeedBack> feedback = jpaRepositoryAssessmentTemplate.getFeedback(id);
            if (feedback.isEmpty()) {
                loggingService.logWarning("No se encontraron FeedBacks para AssessmentTemplate ID: {}", id);
            } else {
                loggingService.logInfo("Se encontraron {} FeedBacks para AssessmentTemplate ID: {}", feedback.size(), id);
            }
            return feedback;
        } catch (Exception e) {
            loggingService.logError("Error al obtener FeedBack para AssessmentTemplate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}