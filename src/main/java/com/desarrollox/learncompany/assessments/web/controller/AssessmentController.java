package com.desarrollox.learncompany.assessments.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.assessments.web.dto.AssessmentResponse;
import com.desarrollox.learncompany.assessments.web.dto.CreateAssessmentInstanceRequest;
import com.desarrollox.learncompany.assessments.web.webMapper.AssessmentWebMapper;
import com.desarrollox.learncompany.common.web.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessments")
public class AssessmentController {
    private final AssessmentWebMapper assessmentWebMapper;

    @PostMapping("/create-assessment-instance")
    public ResponseEntity<ApiResponse<AssessmentResponse>> createAssessmentInstance(@Valid @RequestBody CreateAssessmentInstanceRequest request) {
        AssessmentResponse response = assessmentWebMapper.toAssessmentResponse(request, false);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Assessment instance created successfully", response));
    }

    @GetMapping("/grade/{id}")
    public ResponseEntity<ApiResponse<Double>> getGradeById(@PathVariable Long id) {
        Double grade = 87.5;
        return ResponseEntity.ok(ApiResponse.success("Grade retrieved successfully", grade));
    }

    @GetMapping("/getAssessmentById/{id}")
    public ResponseEntity<ApiResponse<AssessmentResponse>> getAssessmentById(@PathVariable Long id) {
        AssessmentResponse response = assessmentWebMapper.toAssessmentResponse(id, false);
        return ResponseEntity.ok(ApiResponse.success("Assessment found successfully", response));
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<ApiResponse<String>> getFeedbackById(@PathVariable Long id) {
        String feedback = "Great job! You demonstrated excellent understanding of the concepts.";
        return ResponseEntity.ok(ApiResponse.success("Feedback retrieved successfully", feedback));
    }
}