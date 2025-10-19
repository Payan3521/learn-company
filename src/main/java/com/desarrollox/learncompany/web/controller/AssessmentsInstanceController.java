package com.desarrollox.learncompany.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.domain.service.IAssessmentInstanceService;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceRequest;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceResponse;
import com.desarrollox.learncompany.web.webMapper.AssessmentInstanceWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessmentInstance")
@RequiredArgsConstructor
public class AssessmentsInstanceController {

    private final IAssessmentInstanceService assessmentInstanceService;
    private final AssessmentInstanceWebMapper assessmentInstanceWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @PostMapping
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> createAssessmentInstance(@Valid @RequestBody AssessmentInstanceRequest request) {
        loggingService.logInfo("Iniciando creación de AssessmentInstance para assessmentTemplateId: {}", 
                request != null && request.getAssessmentTemplateId() != null ? request.getAssessmentTemplateId() : "null");
        try {
            AssessmentInstance assessmentInstance = assessmentInstanceWebMapper.requestToDomain(request);
            AssessmentInstance assessmentInstanceSaved = assessmentInstanceService.createAssessmentInstance(assessmentInstance);
            AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstanceSaved);
            loggingService.logInfo("AssessmentInstance creado exitosamente con ID: {}", assessmentInstanceSaved.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Evaluacion creada correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear AssessmentInstance para assessmentTemplateId {}: {}", 
                    request != null && request.getAssessmentTemplateId() != null ? request.getAssessmentTemplateId() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/grade/{id}")
    public ResponseEntity<ApiResponse<Double>> getGradeById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo nota para AssessmentInstance ID: {}", id);
        try {
            Double grade = assessmentInstanceService.getGrade(id);
            loggingService.logInfo("Nota obtenida exitosamente para AssessmentInstance ID: {}, nota: {}", id, grade);
            return ResponseEntity.ok(ApiResponse.success("Nota obtenida correctamente", grade));
        } catch (Exception e) {
            loggingService.logError("Error al obtener nota para AssessmentInstance ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> getAssessmentsById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo AssessmentInstance con ID: {}", id);
        try {
            AssessmentInstance assessmentInstance = assessmentInstanceService.getAssessmentInstanceById(id)
                    .orElseThrow(() -> new IllegalArgumentException("AssessmentInstance con ID " + id + " no encontrado"));
            AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstance);
            loggingService.logInfo("AssessmentInstance ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Instancia de evaluacion obtenida correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener AssessmentInstance ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @PatchMapping("/assign-grade/{grade}/{assessmentId}")
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> asignarGrade(@PathVariable Double grade, @PathVariable Long assessmentId) {
        loggingService.logInfo("Iniciando asignación de nota {} para AssessmentInstance ID: {}", grade, assessmentId);
        try {
            AssessmentInstance assessmentInstance = assessmentInstanceService.assignGrade(assessmentId, grade)
                    .orElseThrow(() -> new IllegalArgumentException("AssessmentInstance con ID " + assessmentId + " no encontrado"));
            AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstance);
            loggingService.logInfo("Nota {} asignada exitosamente para AssessmentInstance ID: {}", grade, assessmentId);
            return ResponseEntity.ok(ApiResponse.success("Nota asignada correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al asignar nota {} para AssessmentInstance ID {}: {}", 
                    grade, assessmentId, e.getMessage(), e);
            throw e;
        }
    }
}