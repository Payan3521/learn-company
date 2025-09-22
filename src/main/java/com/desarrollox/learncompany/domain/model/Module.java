package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Module {
    private Long id;
    private Course course;
    private String title;
    private List<AssessmentTemplate> assessmentTemplate;

    public Module() {
    }

    public Module(Long id, Course course, String title, List<AssessmentTemplate> assessmentTemplate) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.assessmentTemplate = assessmentTemplate;
    }
    
    public Module(Course course, String title, List<AssessmentTemplate> assessmentTemplate) {
        this.course = course;
        this.title = title;
        this.assessmentTemplate = assessmentTemplate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<AssessmentTemplate> getAssessmentTemplate() { return assessmentTemplate; }
    public void setAssessmentTemplate(List<AssessmentTemplate> assessmentTemplate) { this.assessmentTemplate = assessmentTemplate; }

}