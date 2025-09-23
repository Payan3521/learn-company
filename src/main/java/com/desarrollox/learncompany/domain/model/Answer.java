package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Answer {
    private Long id;
    private String content;
    private Question question;
    private LocalDateTime dateIssued;
    private AssessmentInstance assessmentInstance;

    public Answer() {
    }
    
    public Answer(Long id, String content, Question question, LocalDateTime dateIssued, AssessmentInstance assessmentInstance) {
        this.id = id;
        this.content = content;
        this.question = question;
        this.dateIssued = dateIssued;
        this.assessmentInstance = assessmentInstance;
    }

    public Answer(String content, Question question, LocalDateTime dateIssued, AssessmentInstance assessmentInstance) {
        this.content = content;
        this.question = question;
        this.dateIssued = dateIssued;
        this.assessmentInstance = assessmentInstance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public LocalDateTime getDateIssued() { return dateIssued; }
    public void setDateIssued(LocalDateTime dateIssued) { this.dateIssued = dateIssued; }

    public AssessmentInstance getAssessmentInstance() { return assessmentInstance; }

    public void setAssessmentInstance(AssessmentInstance assessmentInstance) { this.assessmentInstance = assessmentInstance; }
    
}