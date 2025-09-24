package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeasonRequest {

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 336, message = "La duración mínima es de 2 semanas (336 horas)")
    @Max(value = 720, message = "La duración máxima es de 1 mes (720 horas)")
    private int duration;

    @NotBlank(message = "El nombre de la temporada no puede estar vacío")
    private String name;

}