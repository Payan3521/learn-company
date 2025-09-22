package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Assessment {
    private Long id;
    private Module module;
    private List<Question> questions;
    private Type type;
    private int reintentos;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Module getModule() { return module; }

    public void setModule(Module module) { this.module = module; }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public Type getType() { return type; }

    public void setType(Type type) { this.type = type; }

    public int getReintentos() { return reintentos; }

    public void setReintentos(int reintentos) { this.reintentos = reintentos; /* a|13E4R5 */}

    public enum Type{
        quiz,
        workshop,
        evaluation
    }
}
