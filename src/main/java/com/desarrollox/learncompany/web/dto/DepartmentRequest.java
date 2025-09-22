package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentRequest {
    @NotBlank(message = "El nombre del departamento es obligatorio")
    private String name;

    @NotBlank(message = "El premio es obligatorio")
    private String prize;

    @NotNull(message = "La jerarquía es obligatoria")
    @Min(value = 1, message = "La jerarquía debe ser mayor o igual a 1")
    private Integer hierarchy;
}