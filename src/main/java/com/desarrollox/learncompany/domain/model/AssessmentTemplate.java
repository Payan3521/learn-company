package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class AssessmentTemplate {
    private Long id;
    private Module module;
    private List<Question> questions;
    private Type type;
    private int retries;

    public AssessmentTemplate() {
    }

    public AssessmentTemplate(Module module, List<Question> questions, Type type, int retries) {
        this.module = module;
        this.questions = questions;
        this.type = type;
        this.retries = retries;
    }

    public AssessmentTemplate(Long id, Module module, List<Question> questions, Type type, int retries) {
        this.id = id;
        this.module = module;
        this.questions = questions;
        this.type = type;
        this.retries = retries;
    }

     public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public int getRetries() { return retries; }
    public void setRetries(int retries) { this.retries = retries; }

    public enum Type {
        QUIZ,
        WORKSHOP,
        FINAL_ASSESSMENT
    }
}