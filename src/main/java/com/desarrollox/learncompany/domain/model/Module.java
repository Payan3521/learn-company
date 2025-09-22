package com.desarrollox.learncompany.domain.model;

public class Module {
    private Long id;
    private int[] evaluation;
    private Course course;
    private String title;

    public Module() {
        this.evaluation = new int[3];
    }

    public Module(Long id, int[] evaluation, Course course, String title) {
        this.evaluation = new int[3];
        this.id = id;
        this.course = course;
        this.title = title;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int[] getEvaluation() { return evaluation; }

    public void setEvaluation(int[] evaluation) {
        if (evaluation != null && evaluation.length == 3) {
            this.evaluation = evaluation;
        }
    }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
