package com.desarrollox.learncompany.web.dto;

import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate.Type;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssessmentTemplateRequest {

    @Valid
    @NotEmpty(message = "La lista de preguntas no puede estar vacía")
    private List<QuestionRequest> questions;

    @NotNull(message = "El tipo de evaluación es obligatorio")
    private Type type;

    @NotNull(message = "Los reintentos son obligatorios")
    @Min(value = 0, message = "El número de reintentos debe ser cero o mayor")
    @Max(value = 3, message = "El número de reintentos no puede ser mayor que 3")
    private int retries;
}