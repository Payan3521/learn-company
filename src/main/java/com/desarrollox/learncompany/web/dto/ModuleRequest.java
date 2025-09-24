package com.desarrollox.learncompany.web.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleRequest {

    @NotNull(message = "El ID del curso es obligatorio")
    @Min(value = 1, message = "El ID del curso debe ser mayor que 0")
    private Long courseId;
    
    @NotBlank(message = "El titulo del modulo no puede estar vacío")
    private String title;

    @Valid
    @NotEmpty(message = "La lista de evaluaciones template no puede estar vacía")
    @Size(max = 3, message = "La lista de evaluaciones template no puede tener más de 3 elementos")
    private List<AssessmentTemplateRequest> assessmentTemplate;
}