package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseRequest {

    @NotBlank(message = "El título del curso no puede estar vacío")
    @Size(max = 150, message = "El título no puede tener más de 150 caracteres")
    private String title;

    @NotBlank(message = "El tema del curso no puede estar vacío")
    @Size(max = 100, message = "El tema no puede tener más de 100 caracteres")
    private String topic;

    @NotBlank(message = "La descripción del curso no puede estar vacía")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    private String description;

    @Min(value = 1, message = "El nivel debe ser mínimo 1")
    @Max(value = 3, message = "El nivel no puede ser mayor a 3")
    private int level;

    @Min(value = 1, message = "La duración del curso debe ser al menos 1 hora")
    private int duration;

    @NotNull(message = "El ID de la temporada es obligatorio")
    @Min(value = 1, message = "El ID de la temporada debe ser mayor que 0")
    private Long seasonId;

    @NotNull(message = "El ID del instructor es obligatorio")
    @Min(value = 1, message = "El ID del instructor debe ser mayor que 0")
    private Long instructorId;
}