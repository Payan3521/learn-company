package com.desarrollox.learncompany.assessments.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerRequest {

    @NotNull(message = "Content is required")
    private String content;
        
    @NotNull(message = "Question ID is required")
    @Positive(message = "Question ID must be positive")
    private Long question;
    
}