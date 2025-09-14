package com.desarrollox.learncompany.assessments.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedBackResponse {
    
    Long id;
    Long question;
    String questionText;
    String correctAnswers;
    String userAnswer;

}