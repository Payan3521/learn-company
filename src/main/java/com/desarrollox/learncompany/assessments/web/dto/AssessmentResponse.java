package com.desarrollox.learncompany.assessments.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AssessmentResponse {
    private Long id;
    private Long assessmentTemplateId;
    private Long employId;
    private Double grade;
    private String status;
    private List<FeedBackResponse> feedback;
    private LocalDateTime submittedAt;
}