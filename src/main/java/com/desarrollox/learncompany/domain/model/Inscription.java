package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Inscription {
    private Long id;
    private Employ employ;
    private LocalDateTime dateAndHour;
    private Course course;
    private Status status;

    public Inscription() {
    }

    public Inscription(Long id, Employ employ, LocalDateTime dateAndHour, Course course, Status status) {
        this.id = id;
        this.employ = employ;
        this.dateAndHour = dateAndHour;
        this.course = course;
        this.status = status;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Employ getEmploy() { return employ; }

    public void setEmploy(Employ employ) { this.employ = employ; }

    public LocalDateTime getDateAndHour() { return dateAndHour; }

    public void setDateAndHour(LocalDateTime dateAndHour) { this.dateAndHour = dateAndHour; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public enum Status{
        Accepted,
        inProgress,
        rejected
    }
}
