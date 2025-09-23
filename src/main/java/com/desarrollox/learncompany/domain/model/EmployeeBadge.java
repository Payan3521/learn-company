package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class EmployeeBadge {
    private Long id;
    private Employee employee;
    private Badge badge;
    private LocalDateTime dateEarned;

    public EmployeeBadge() {
    }

    public EmployeeBadge(Long id, Employee employee, Badge badge, LocalDateTime dateEarned) {
        this.id = id;
        this.employee = employee;
        this.badge = badge;
        this.dateEarned = dateEarned;
    }

    public EmployeeBadge(Employee employee, Badge badge, LocalDateTime dateEarned) {
        this.employee = employee;
        this.badge = badge;
        this.dateEarned = dateEarned;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Badge getBadge() { return badge; }
    public void setBadge(Badge badge) { this.badge = badge; }

    public LocalDateTime getDateEarned() { return dateEarned; }
    public void setDateEarned(LocalDateTime dateEarned) { this.dateEarned = dateEarned; }
}
