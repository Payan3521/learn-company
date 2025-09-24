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
    private Long employeeId;
    private LocalDateTime dateAndHour;
    private Long courseId;
    private Status status;
}