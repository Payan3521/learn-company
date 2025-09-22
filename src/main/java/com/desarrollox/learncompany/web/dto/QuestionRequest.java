package com.desarrollox.learncompany.web.dto;

public class QuestionRequest {
    private String question;
    private String responseOptions;
    private String correctAnswer;
    //el ya conoce su assessment template
    private Long assessmentTemplateId;
}