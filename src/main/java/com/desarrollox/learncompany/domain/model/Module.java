package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Module {
    private Long id;
    private Course course;
    private String urlVideo;
    private String urlGuia;
    private int hierarchy;
    private String title;
    private List<AssessmentTemplate> assessmentTemplate;

    public Module() {
    }

    public Module(Long id, Course course, String urlVideo, String urlGuia, int hierarchy, String title, List<AssessmentTemplate> assessmentTemplate) {
        this.id = id;
        this.course = course;
        this.urlVideo = urlVideo;
        this.urlGuia = urlGuia;
        this.hierarchy = hierarchy;
        this.title = title;
        this.assessmentTemplate = assessmentTemplate;
    }
    
    public Module(Course course, String urlVideo, String urlGuia, int hierarchy, String title, List<AssessmentTemplate> assessmentTemplate) {
        this.course = course;
        this.urlVideo = urlVideo;
        this.urlGuia = urlGuia;
        this.hierarchy = hierarchy;
        this.title = title;
        this.assessmentTemplate = assessmentTemplate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getUrlVideo(){ return urlVideo; }
    public void setUrlVideo(String urlVideo){ this.urlVideo = urlVideo; }
    
    public String getUrlGuia(){ return urlGuia; }
    public void setUrlGuia(String urlGuia){ this.urlGuia = urlGuia; }

    public int getHierarchy(){ return hierarchy; }
    public void sethierarchy(int hierarchy){ this.hierarchy = hierarchy; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<AssessmentTemplate> getAssessmentTemplate() { return assessmentTemplate; }
    public void setAssessmentTemplate(List<AssessmentTemplate> assessmentTemplate) { this.assessmentTemplate = assessmentTemplate; }

}