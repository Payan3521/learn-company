package com.desarrollox.learncompany.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResponse {
    private Long topCourseId;
    private String topCourseName;
    private Integer topCourseInscriptions;
    private Long lessCourseId;
    private String lessCourseName;
    private Integer lessCourseInscriptions;
}