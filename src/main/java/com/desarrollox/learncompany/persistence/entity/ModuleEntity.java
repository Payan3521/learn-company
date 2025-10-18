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

    @Column(name = "url_video", nullable = false)
    private String urlVideo;

    @Column(name = "url_guia", nullable = false)
    private String urlGuia;

    @Column(name = "hierarchy_module", nullable = false)
    private int hierarchy;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AssessmentTemplateEntity> assessmentTemplate;
}