package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class Certificate {
    private Long id;
    private Employee employee;
    private Course course;
    private LocalDateTime dateIssued;

    public Certificate() {
    }

    public Certificate(Employee employee, Course course, LocalDateTime dateIssued) {
        this.employee = employee;
        this.course = course;
        this.dateIssued = dateIssued;
    }

    public Certificate(Long id, Employee employee, Course course, LocalDateTime dateIssued) {
        this.id = id;
        this.employee = employee;
        this.course = course;
        this.dateIssued = dateIssued;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDateTime getDateIssued() { return dateIssued; }
    public void setDateIssued(LocalDateTime dateIssued) { this.dateIssued = dateIssued; }
    
}