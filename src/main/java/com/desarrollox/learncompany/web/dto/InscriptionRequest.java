package com.desarrollox.learncompany.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InscriptionRequest {
    private Long employeeId;
    private Long courseId;
}