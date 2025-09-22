package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Inscription.Status;

public class InscriptionResponse {
    private Long id;
    private UserResponse employee;
    private LocalDateTime dateAndHour;
    private Course course;
    private Status status;
}