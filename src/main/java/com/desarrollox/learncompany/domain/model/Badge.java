package com.desarrollox.learncompany.domain.model;

public class Badge {
    private Long id;
    private String name;
    private String urlIcon;
    private String criteria;
    private Employee employee;

    public Badge() {
    }

    public Badge(String name, String urlIcon, String criteria, Employee employee) {
        this.name = name;
        this.urlIcon = urlIcon;
        this.criteria = criteria;
        this.employee= employee;
    }

    public Badge(Long id, String name, String urlIcon, String criteria, Employee employee) {
        this.id = id;
        this.name = name;
        this.urlIcon = urlIcon;
        this.criteria = criteria;
        this.employee = employee;
    }
    
    public Long getId() {return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrlIcon() { return urlIcon; }
    public void setUrlIcon(String urlIcon) { this.urlIcon = urlIcon; }

    public String getCriteria() { return criteria; }
    public void setCriteria(String criteria) { this.criteria = criteria; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    
}