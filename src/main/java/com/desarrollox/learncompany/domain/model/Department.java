package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Department {
    private Long id;
    private String name;
    private String prize;
    private int hierarchy;
    private List<Course> courses;

    public Course findCourseByTitle(String title) {
        return courses.stream()
                .filter(c -> c.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public Department() {
    }

    public Department(Long id, String name, String prize, int hierarchy, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.prize = prize;
        this.hierarchy = hierarchy;
        this.courses = courses;
    }

    public Department(String name, String prize, int hierarchy, List<Course> courses) {
        this.name = name;
        this.prize = prize;
        this.hierarchy = hierarchy;
        this.courses = courses;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrize() { return prize; }
    public void setPrize(String prize) { this.prize = prize; }

    public int getHierarchy() { return hierarchy; }
    public void setHierarchy(int hierarchy) { this.hierarchy = hierarchy; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

}
