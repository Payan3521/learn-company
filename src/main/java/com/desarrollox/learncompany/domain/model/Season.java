package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Season {
    private Long id;
    private int duration;
    private String name;
    private List<Course> courses;

    public Season() {
    }

    public Season(Long id, int duration, String name, List<Course> courses) {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.courses = courses;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Course> getCourses() { return courses; }

    public void setCourses(List<Course> courses) { this.courses = courses; }
}
