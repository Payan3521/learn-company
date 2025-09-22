package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Answer {
    private Long id;
    private String content;
    private Question question;
    private LocalDateTime dateIssued;

    public Answer() {
    }
    
    public Answer(Long id, String content, Question question, LocalDateTime dateIssued) {
        this.id = id;
        this.content = content;
        this.question = question;
        this.dateIssued = dateIssued;
    }

    public Answer(String content, Question question, LocalDateTime dateIssued) {
        this.content = content;
        this.question = question;
        this.dateIssued = dateIssued;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public LocalDateTime getDateIssued() { return dateIssued; }
    public void setDateIssued(LocalDateTime dateIssued) { this.dateIssued = dateIssued; }
}