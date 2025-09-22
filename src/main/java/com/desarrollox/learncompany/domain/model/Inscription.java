package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Inscription {
    private Long id;
    private Employee employee;
    private LocalDateTime dateAndHour;
    private Course course;
    private Status status;

    public Inscription() {
    }

    public Inscription(Long id, Employee employee, LocalDateTime dateAndHour, Course course, Status status) {
        this.id = id;
        this.employee = employee;
        this.dateAndHour = dateAndHour;
        this.course = course;
        this.status = status;
    }

    public Inscription(Employee employee, LocalDateTime dateAndHour, Course course, Status status) {
        this.employee = employee;
        this.dateAndHour = dateAndHour;
        this.course = course;
        this.status = status;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Employee getEmploy() { return employee; }

    public void setEmploy(Employee employee) { this.employee = employee; }

    public LocalDateTime getDateAndHour() { return dateAndHour; }

    public void setDateAndHour(LocalDateTime dateAndHour) { this.dateAndHour = dateAndHour; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public enum Status{
        ACCEPTED,
        IN_PROGRESS,
        REJECTED
    }
}
