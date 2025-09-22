package com.desarrollox.learncompany.persistence.entity;

import java.util.List;

import com.desarrollox.learncompany.domain.model.AssessmentTemplate.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private Long id;
    private Module module;
    private List<QuestionEntity> questions;
    private Type type;
    private int retries;
}
