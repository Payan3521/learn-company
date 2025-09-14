package com.desarrollox.learncompany.assessments.web.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateAssessmentInstanceRequest {
    @NotNull(message = "Assessment template ID is required")
    @Positive(message = "Assessment template ID must be positive")
    private Long assessmentTemplate;

    @NotNull(message = "Employ ID is required")
    @Positive(message = "Employ ID must be positive")
    private Long employ;

    @Valid
    private List<AnswerRequest> answers;

}