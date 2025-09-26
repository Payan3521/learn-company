package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class AssessmentInstance {
    private Long id;
    private AssessmentTemplate assessmentTemplate;
    private Employee employee;
    private double grade;
    private Status status;
    private List<Answer> answers;
    private LocalDateTime createdAt;

    public boolean isGraded() {
        return status == Status.GRADED;
    }

    public boolean isPending() {
        return status == Status.PENDING;
    }

    public AssessmentInstance() {
    }

    public AssessmentInstance(Long id, AssessmentTemplate assessmentTemplate, Employee employee, double grade, Status status,
            List<Answer> answers, LocalDateTime createdAt) {
        this.id = id;
        this.assessmentTemplate = assessmentTemplate;
        this.employee = employee;
        this.grade = grade;
        this.status = status;
        this.answers = answers;
        this.createdAt = createdAt;
    }
    
    public AssessmentInstance(AssessmentTemplate assessmentTemplate, Employee employee, double grade, Status status,
            List<Answer> answers, LocalDateTime createdAt) {
        this.assessmentTemplate = assessmentTemplate;
        this.employee = employee;
        this.grade = grade;
        this.status = status;
        this.answers = answers;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AssessmentTemplate getAssessmentTemplate() { return assessmentTemplate; }
    public void setAssessmentTemplate(AssessmentTemplate assessmentTemplate) { this.assessmentTemplate = assessmentTemplate; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<Answer> getAnswers() { return answers; }
    public void setAnswers(List<Answer> answers) { this.answers = answers; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum Status {
        PENDING,
        GRADED
    }

}