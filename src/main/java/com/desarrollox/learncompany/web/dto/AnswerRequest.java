package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerRequest {

    @NotBlank(message = "El contenido de la respuesta no puede estar vacío")
    private String content;

    @NotNull(message = "El ID de la pregunta es obligatorio")
    @Min(value = 1, message = "El ID de la pregunta debe ser mayor que 0")
    private Long questionId;
}