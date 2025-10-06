package com.desarrollox.learncompany.web.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class DepartmentResponse {
    private Long id;
    private String name;
    private String prize;
    private Integer hierarchy;
    private List<CourseResponse> courses;
} 