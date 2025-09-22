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

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseEntity course;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssessmentTemplateEntity> assessmentTemplate;
}