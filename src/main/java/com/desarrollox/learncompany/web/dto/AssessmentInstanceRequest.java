package com.desarrollox.learncompany.web.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssessmentInstanceRequest {

    @NotNull(message = "El ID de la evaluacion template es obligatorio")
    @Min(value = 1, message = "El ID de la evaluacion template debe ser mayor que 0")
    private Long assessmentTemplateId;

    @NotNull(message = "El ID del empleado es obligatorio")
    @Min(value = 1, message = "El ID del empleado debe ser mayor que 0")
    private Long employeeId;

    @Valid
    @NotEmpty(message = "La lista de respuestas no puede estar vacía")
    private List<AnswerRequest> answers;

}