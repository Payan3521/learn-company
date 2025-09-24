package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentInstance.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AssessmentInstanceResponse {
    private Long id;
    private Long assessmentTemplateId;
    private Long employeeId;
    private Double grade;
    private Status status;
    private List<AnswerResponse> answers;
    private LocalDateTime createdAt;
}