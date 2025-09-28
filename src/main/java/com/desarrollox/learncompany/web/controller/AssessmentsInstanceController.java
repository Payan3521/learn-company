package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.domain.service.IAssessmentInstanceService;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceRequest;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceResponse;
import com.desarrollox.learncompany.web.webMapper.AssessmentInstanceWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentsInstanceController {

    private final IAssessmentInstanceService assessmentInstanceService;
    private final AssessmentInstanceWebMapper assessmentInstanceWebMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> createAssessmentInstance(@Valid @RequestBody AssessmentInstanceRequest request){
        AssessmentInstance assessmentInstance = assessmentInstanceWebMapper.requestToDomain(request);
        AssessmentInstance assessmentInstanceSaved = assessmentInstanceService.createAssessmentInstance(assessmentInstance);
        AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstanceSaved);
        return ResponseEntity.ok(ApiResponse.success("Instancia de evaluacion creada correctamente", response));
    }

    @GetMapping("/grade/{id}")
    public ResponseEntity<ApiResponse<Double>> getGradeById(@PathVariable Long id){
        Double grade = assessmentInstanceService.getGrade(id);
        return ResponseEntity.ok(ApiResponse.success("Nota obtenida correctamente", grade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> getAssessmentsById(@PathVariable Long id){
        AssessmentInstance assessmentInstance = assessmentInstanceService.getAssessmentInstanceById(id).get();
        AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstance);
        return ResponseEntity.ok(ApiResponse.success("Instancia de evaluacion obtenida correctamente", response));
    }

    @PatchMapping("/assign-grade/{grade}/{assessmentId}")
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> asignarGrade(@PathVariable Double grade, @PathVariable Long assessmentId){
        AssessmentInstance assessmentInstance = assessmentInstanceService.assignGrade( assessmentId, grade).get();
        AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstance);
        return ResponseEntity.ok(ApiResponse.success("Nota asignada correctamente", response));
        
    }

}