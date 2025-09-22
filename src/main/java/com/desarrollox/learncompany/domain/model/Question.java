package com.desarrollox.learncompany.domain.model;

public class Question {
    private Long id;
    private String question;
    private String responseOptions;
    private String correctAnswer;
    private AssessmentTemplate assessmentTemplate;

    public Question() {
    }

    public Question(Long id, String question, String responseOptions, String correctAnswer,
            AssessmentTemplate assessmentTemplate) {
        this.id = id;
        this.question = question;
        this.responseOptions = responseOptions;
        this.correctAnswer = correctAnswer;
        this.assessmentTemplate = assessmentTemplate;
    }

    public Question(String question, String responseOptions, String correctAnswer,
            AssessmentTemplate assessmentTemplate) {
        this.question = question;
        this.responseOptions = responseOptions;
        this.correctAnswer = correctAnswer;
        this.assessmentTemplate = assessmentTemplate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getResponseOptions() { return responseOptions; }
    public void setResponseOptions(String responseOptions) { this.responseOptions = responseOptions; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public AssessmentTemplate getAssessmentTemplate() { return assessmentTemplate; }
    public void setAssessmentTemplate(AssessmentTemplate assessmentTemplate) { this.assessmentTemplate = assessmentTemplate; }
    
}