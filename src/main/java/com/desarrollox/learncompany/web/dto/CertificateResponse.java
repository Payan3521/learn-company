package com.desarrollox.learncompany.web.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class CertificateResponse {
    private Long id;
    private Long employeeId;
    private Long courseId;
    private LocalDateTime dateIssued;
}