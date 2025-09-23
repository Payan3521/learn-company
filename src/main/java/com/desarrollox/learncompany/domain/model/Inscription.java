package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Inscription {
    private Long id;
    private Employee employee;
    private LocalDateTime dateIssued;
    private Course course;
    private Status status;

    public Inscription() {
    }

    public Inscription(Long id, Employee employee, LocalDateTime dateIssued, Course course, Status status) {
        this.id = id;
        this.employee = employee;
        this.dateIssued = dateIssued;
        this.course = course;
        this.status = status;
    }

    public Inscription(Employee employee, LocalDateTime dateIssued, Course course, Status status) {
        this.employee = employee;
        this.dateIssued = dateIssued;
        this.course = course;
        this.status = status;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public LocalDateTime getDateIssued() { return dateIssued; }

    public void setDateIssued(LocalDateTime dateIssued) { this.dateIssued = dateIssued; }

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
