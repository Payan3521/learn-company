package com.desarrollox.learncompany.web.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class ModuleResponse {
    private Long id;
    private Long courseId;
    private String title;
    private List<AssessmentTemplateResponse> assessmentTemplate;
}