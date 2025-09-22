package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    private QuestionEntity question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_instance_id", referencedColumnName = "id", nullable = false)
    private AssessmentInstanceEntity assessmentInstance;

    @Column(name = "date_issued", nullable = false)
    private LocalDateTime dateIssued;
}