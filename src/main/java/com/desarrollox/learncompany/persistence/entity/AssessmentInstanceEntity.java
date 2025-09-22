package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentInstance.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "assessments_instaces")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AssessmentInstanceEntity {
    private Long id;
    private AssessmentTemplateEntity assessmentTemplate;
    private EmployeeEntity employee;
    private double grade;
    private Status status;
    private List<AnswerEntity> answers;
    private LocalDateTime createdAt;
}
