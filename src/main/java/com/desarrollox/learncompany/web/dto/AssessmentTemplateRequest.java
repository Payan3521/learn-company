package com.desarrollox.learncompany.web.dto;

import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssessmentTemplateRequest {
    private List<QuestionRequest> questions;
    private Type type;
    private int retries;
}