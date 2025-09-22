package com.desarrollox.learncompany.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class QuestionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "response_options")
    private String responseOptions;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "assestment_template")
    private AssessmentTemplateEntity assessmentTemplate;
}
