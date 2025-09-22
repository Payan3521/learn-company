package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentInstance.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_template_id", referencedColumnName = "id", nullable = false)
    private AssessmentTemplateEntity assessmentTemplate;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private EmployeeEntity employee;

    private double grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany
    @JoinColumn(name = "assessment_instance_id")
    private List<AnswerEntity> answers;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
