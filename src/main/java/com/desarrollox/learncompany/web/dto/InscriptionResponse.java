package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import com.desarrollox.learncompany.domain.model.Inscription.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class InscriptionResponse {
    private Long id;
    private UserResponse employee;
    private LocalDateTime dateAndHour;
    private CourseResponse course;
    private Status status;
}