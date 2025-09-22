package com.desarrollox.learncompany.web.dto;

import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate.Type;

public class AssessmentTemplateRequest {
    //el ya conoce su modulo
    private Long moduleId;
    private List<QuestionRequest> questions;
    private Type type;
    private int retries;
}