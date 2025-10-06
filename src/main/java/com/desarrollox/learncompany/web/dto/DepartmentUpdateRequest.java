package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentUpdateRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
    
    @NotBlank(message = "El premio es obligatorio")
    @Size(min = 2, max = 255, message = "El premio debe tener entre 2 y 255 caracteres")
    private String prize;
    
    @NotNull(message = "La jerarquía es obligatoria")
    private int hierarchy;
}