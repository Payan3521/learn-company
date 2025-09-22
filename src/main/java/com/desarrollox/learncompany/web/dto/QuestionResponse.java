package com.desarrollox.learncompany.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class QuestionResponse {
    private Long id;
    private String question;
    private String responseOptions;
    private String correctAnswer;
    private AssessmentTemplateResponse assessmentTemplate;
}