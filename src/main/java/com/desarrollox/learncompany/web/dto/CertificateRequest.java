package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertificateRequest {

    @NotNull(message = "El ID del empleado es obligatorio")
    @Min(value = 1, message = "El ID del empleado debe ser mayor que 0")
    private Long employeeId;

    @NotNull(message = "El ID del curso es obligatorio")
    @Min(value = 1, message = "El ID del curso debe ser mayor que 0")
    private Long courseId;
}