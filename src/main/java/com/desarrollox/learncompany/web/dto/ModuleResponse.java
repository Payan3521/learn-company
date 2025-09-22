package com.desarrollox.learncompany.web.dto;

import java.util.List;

public class ModuleResponse {
    private Long id;
    private CourseResponse course;
    private String title;
    private List<AssessmentTemplateResponse> assessmentTemplate;
}