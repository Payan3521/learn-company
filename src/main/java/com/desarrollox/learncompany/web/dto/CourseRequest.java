package com.desarrollox.learncompany.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseRequest {
    private String title;
    private String topic;
    private String description;
    private int level;
    private int duration;
    private Long seasonId;
    private Long instructorId;
}