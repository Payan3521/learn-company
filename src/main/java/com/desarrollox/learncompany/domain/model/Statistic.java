package com.desarrollox.learncompany.domain.model;

public class Statistic {
    private Long id;
    private Course courseTop;
    private Course courseLess;

    public Statistic(Long id, Course courseTop, Course courseLess) {
        this.id = id;
        this.courseTop = courseTop;
        this.courseLess = courseLess;
    }

    public Statistic(Course courseTop, Course courseLess) {
        this.courseTop = courseTop;
        this.courseLess = courseLess;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourseTop() { return courseTop; }
    public void setNameCourseTop(Course courseTop) { this.courseTop = courseTop; }

    public Course getCourseLess() { return courseLess; }
    public void setNameCourseLess(Course courseLess) { this.courseLess = courseLess;}

}