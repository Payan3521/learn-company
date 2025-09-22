package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentInstance.Status;

public class AssessmentInstanceResponse {
    private Long id;
    private AssessmentTemplateResponse assessmentTemplate;
    private UserResponse employee;
    private Double grade;
    private Status status;
    private List<AnswerResponse> answers;
    private LocalDateTime createdAt;
}