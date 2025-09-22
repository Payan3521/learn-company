package com.desarrollox.learncompany.persistence.entity;

import java.util.List;

import com.desarrollox.learncompany.domain.model.AssessmentTemplate.Type;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "assessments_template")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AssessmentTemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false)
    private ModuleEntity module;

    @OneToMany(mappedBy = "assessmentTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions;

    @Enumerated(EnumType.STRING)
    private Type type;
    private int retries;
}
