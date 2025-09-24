package com.desarrollox.learncompany.web.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleRequest {
    private Long courseId;
    private String title;

    //validar que no exceda los 3 objetos
    private List<AssessmentTemplateRequest> assessmentTemplate;
}