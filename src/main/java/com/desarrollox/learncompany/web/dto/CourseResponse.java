package com.desarrollox.learncompany.web.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class CourseResponse {
    private List<ModuleResponse> modules;
    private List<InscriptionResponse> inscriptions;
    private Long id;
    private String title;
    private String topic;
    private String description;
    private Integer level;
    private Integer duration;
    private Long seasonId;
    private Long instructorId;
    private String typeCourse;
}