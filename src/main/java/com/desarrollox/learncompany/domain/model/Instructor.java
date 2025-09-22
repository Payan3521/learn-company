package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Instructor extends User{
    private String specialty;
    private String biography;
    private List<Course> courses;

    public Instructor() {
    }

    public Instructor(String specialty, String biography, List<Course> courses) {
        this.specialty = specialty;
        this.biography = biography;
        this.courses = courses;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
