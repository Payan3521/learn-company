package com.desarrollox.learncompany.web.dto;

import java.util.List;

public class CourseResponse {
    private List<ModuleResponse> modules;
    private List<InscriptionResponse> inscriptions;
    private Long id;
    private String title;
    private String topic;
    private String description;
    private Integer level;
    private Integer duration;
    private SeasonResponse season;
    private UserResponse instructor;
}