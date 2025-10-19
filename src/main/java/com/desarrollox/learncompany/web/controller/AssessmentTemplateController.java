package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;
import com.desarrollox.learncompany.domain.service.IAssessmentTemplateService;
import com.desarrollox.learncompany.web.dto.AssessmentTemplateResponse;
import com.desarrollox.learncompany.web.dto.FeedBackResponse;
import com.desarrollox.learncompany.web.webMapper.AssessmentTemplateWebMapper;
import com.desarrollox.learncompany.web.webMapper.FeedBackWebMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessmentTemplate")
public class AssessmentTemplateController {

    private final IAssessmentTemplateService assessmentTemplateService;
    private final AssessmentTemplateWebMapper assessmentTemplateWebMapper;
    private final FeedBackWebMapper feedBackWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @GetMapping("/module/{id}")
    public ResponseEntity<ApiResponse<List<AssessmentTemplateResponse>>> getAssessmentTemplateByModuleId(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo AssessmentTemplates para moduleId: {}", id);
        try {
            List<AssessmentTemplate> assessmentTemplates = assessmentTemplateService.getAssessmentsByModuleId(id);

            if (assessmentTemplates.isEmpty()) {
                loggingService.logWarning("No se encontraron AssessmentTemplates para moduleId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<AssessmentTemplateResponse> responses = assessmentTemplates.stream()
                    .map(assessmentTemplateWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} AssessmentTemplates para moduleId: {}", responses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Evaluaciones encontradas correspondientes al modulo: " + id, responses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentTemplates para moduleId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentTemplateResponse>> getAssessmentTemplateById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo AssessmentTemplate con ID: {}", id);
        try {
            AssessmentTemplate assessmentTemplate = assessmentTemplateService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("AssessmentTemplate con ID " + id + " no encontrado"));
            AssessmentTemplateResponse assessmentTemplateResponse = assessmentTemplateWebMapper.domainToResponse(assessmentTemplate);
            loggingService.logInfo("AssessmentTemplate ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("AssessmentTemplate obtenida correctamente", assessmentTemplateResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentTemplate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<ApiResponse<List<FeedBackResponse>>> getFeedback(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo FeedBack para AssessmentTemplate ID: {}", id);
        try {
            List<FeedBack> feedBack = assessmentTemplateService.getFeedbackById(id);
            if (feedBack.isEmpty()) {
                loggingService.logWarning("No se encontraron FeedBacks para AssessmentTemplate ID: {}", id);
                return ResponseEntity.noContent().build();
            }
            List<FeedBackResponse> response = feedBack.stream()
                    .map(feedBackWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} FeedBacks para AssessmentTemplate ID: {}", response.size(), id);
            return ResponseEntity.ok(ApiResponse.success("FeedBack obtenido correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener FeedBack para AssessmentTemplate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}