package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.service.IAssessmentTemplateService;
import com.desarrollox.learncompany.web.dto.AssessmentTemplateResponse;
import com.desarrollox.learncompany.web.webMapper.AssessmentTemplateWebMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessmentTemplate")
public class AssessmentTemplateController {
    
    private final IAssessmentTemplateService assessmentTemplateService;
    private final AssessmentTemplateWebMapper assessmentTemplateWebMapper;

    @GetMapping("/module/{id}")
    public ResponseEntity<ApiResponse<List<AssessmentTemplateResponse>>> getAssessmentTemplateByModuleId(@PathVariable Long id){
        List<AssessmentTemplate> assessmentTemplates = assessmentTemplateService.getAssessmentsByModuleId(id);

        if(assessmentTemplates.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<AssessmentTemplateResponse> responses = assessmentTemplates.stream().map(assessmentTemplateWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Evaluciones encontradas correspondientes al modulo: " +id, responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentTemplateResponse>> getAssessmentTemplateById(@PathVariable Long id){
        AssessmentTemplate assessmentTemplate = assessmentTemplateService.findById(id).get();
        AssessmentTemplateResponse assessmentTemplateResponse = assessmentTemplateWebMapper.domainToResponse(assessmentTemplate);
        return ResponseEntity.ok(ApiResponse.success("AssesmentTemplate obtenida correctamente", assessmentTemplateResponse));
    }
}