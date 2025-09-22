package com.desarrollox.learncompany.persistence.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "modules")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CourseEntity course;
    private String title;
    private List<AssessmentTemplateEntity> assessmentTemplate;
}