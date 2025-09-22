package com.desarrollox.learncompany.web.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssessmentInstanceRequest {

    private Long assessmentTemplateId;
    private Long employeeId;
    private List<AnswerRequest> answers;

}