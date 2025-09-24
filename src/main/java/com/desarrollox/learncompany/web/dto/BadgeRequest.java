package com.desarrollox.learncompany.web.dto;

import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BadgeRequest {

    @NotBlank(message = "El nombre del badge no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @NotBlank(message = "La URL del ícono es obligatoria")
    @URL(message = "La URL del ícono debe ser válida")
    private String urlIcon;

    @NotBlank(message = "El criterio del badge no puede estar vacío")
    @Size(max = 255, message = "El criterio no puede tener más de 255 caracteres")
    private String criteria;
}