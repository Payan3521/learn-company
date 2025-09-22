package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "badges")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AnswerEntity {
    private Long id;
    private String content;
    private QuestionEntity question;
    private LocalDateTime dateIssued;
}