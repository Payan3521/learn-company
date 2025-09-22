package com.desarrollox.learncompany.web.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class SeasonResponse {
    private Long id;
    private Integer duration;
    private String name;
    private List<CourseResponse> courses;
}