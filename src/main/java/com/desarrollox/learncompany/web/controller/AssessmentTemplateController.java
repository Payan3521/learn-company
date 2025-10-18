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

    @GetMapping("/feedback/{id}")
    public ResponseEntity<ApiResponse<List<FeedBackResponse>>> getFeedback(@PathVariable Long id){
        List<FeedBack> feedBack = assessmentTemplateService.getFeedbackById(id);
        List<FeedBackResponse> response = feedBack.stream().map(feedBackWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("FeedBack obtenido correctamente", response));
    }
}