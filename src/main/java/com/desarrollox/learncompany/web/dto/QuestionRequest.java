package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionRequest {

    @NotBlank(message = "La pregunta no puede estar vacía")
    @Size(max = 500, message = "La pregunta no puede tener más de 500 caracteres")
    private String question;

    @NotBlank(message = "Las opciones de respuesta no pueden estar vacías")
    @Size(max = 1000, message = "Las opciones de respuesta no pueden tener más de 1000 caracteres")
    private String responseOptions;

    @NotBlank(message = "La respuesta correcta no puede estar vacía")
    @Size(max = 255, message = "La respuesta correcta no puede tener más de 255 caracteres")
    private String correctAnswer;
}