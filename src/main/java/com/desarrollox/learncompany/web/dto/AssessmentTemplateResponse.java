package com.desarrollox.learncompany.web.dto;

import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate.Type;

public class AssessmentTemplateResponse {
    private Long id;
    private ModuleResponse module;
    private List<QuestionResponse> questions;
    private Type type;
    private Integer retries;
}