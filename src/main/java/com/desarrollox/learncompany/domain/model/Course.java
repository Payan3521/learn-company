package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Course {
    private List<Module> modules;
    private List<Inscription> inscriptions;
    private Long id;
    private String title;
    private String topic;
    private String description;
    private int level;
    private int duration;
    private Season season;
    private Instructor instructor;
    private TypeCourse typeCourse;
    private Department department;

    public boolean isMandatory() {
        return typeCourse == TypeCourse.MANDATORY;
    }

    public boolean isOptional() {
        return typeCourse == TypeCourse.OPTIONAL;
    }

    public int getTotalInscriptions() {
        return inscriptions == null ? 0 : inscriptions.size();
    }

    public Course() {
    }

    public Course(List<Module> modules, List<Inscription> inscriptions, Long id, String title,
                  String topic, String description, int level, int duration,
                  Season season, Instructor instructor, TypeCourse typeCourse, Department department) {
        this.modules = modules;
        this.inscriptions = inscriptions;
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.description = description;
        this.level = level;
        this.duration = duration;
        this.season = season;
        this.instructor = instructor;
        this.typeCourse = typeCourse;
        this.department = department;
    }

    public Course(List<Module> modules, List<Inscription> inscriptions, String title, String topic, String description,
            int level, int duration, Season season, Instructor instructor, TypeCourse typeCourse, Department department) {
        this.modules = modules;
        this.inscriptions = inscriptions;
        this.title = title;
        this.topic = topic;
        this.description = description;
        this.level = level;
        this.duration = duration;
        this.season = season;
        this.instructor = instructor;
        this.typeCourse = typeCourse;
        this.department = department;
    }

    public enum TypeCourse {
        OPTIONAL,
        MANDATORY
    }

    public TypeCourse getTypeCourse() { return typeCourse; }
    public void setTypeCourse(TypeCourse typeCourse) { this.typeCourse = typeCourse; }

    public List<Module> getModules() { return modules; }
    public void setModules(List<Module> modules) { this.modules = modules; }

    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> inscriptions) { this.inscriptions = inscriptions; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public Season getSeason() { return season; }
    public void setSeason(Season season) { this.season = season; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}