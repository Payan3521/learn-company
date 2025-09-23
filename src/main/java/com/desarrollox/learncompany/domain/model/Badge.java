package com.desarrollox.learncompany.domain.model;

public class Badge {
    private Long id;
    private String name;
    private String urlIcon;
    private String criteria;

    public Badge() {
    }

    public Badge(String name, String urlIcon, String criteria) {
        this.name = name;
        this.urlIcon = urlIcon;
        this.criteria = criteria;
    }

    public Badge(Long id, String name, String urlIcon, String criteria) {
        this.id = id;
        this.name = name;
        this.urlIcon = urlIcon;
        this.criteria = criteria;
    }
    
    public Long getId() {return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrlIcon() { return urlIcon; }
    public void setUrlIcon(String urlIcon) { this.urlIcon = urlIcon; }

    public String getCriteria() { return criteria; }
    public void setCriteria(String criteria) { this.criteria = criteria; }

}