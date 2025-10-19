package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;
import com.desarrollox.learncompany.domain.service.IAssessmentTemplateService;
import com.desarrollox.learncompany.web.dto.AssessmentTemplateResponse;
import com.desarrollox.learncompany.web.dto.FeedBackResponse;
import com.desarrollox.learncompany.web.webMapper.AssessmentTemplateWebMapper;
import com.desarrollox.learncompany.web.webMapper.FeedBackWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessmentTemplate")
public class AssessmentTemplateController {
    
    private final IAssessmentTemplateService assessmentTemplateService;
    private final AssessmentTemplateWebMapper assessmentTemplateWebMapper;
    private final FeedBackWebMapper feedBackWebMapper;

    @Operation(
        summary = "Obtener plantillas de evaluaciones por id de modulo ",
        description = "Busca y devuelve una plantilla de evalución por el id de un modulo especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del modulo",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Plantilla de evaluación encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "AssesmentTemplate obtenida correctamente",
                                "data": {
                                    "id": 1,
                                    "type": "QUIZ",
                                    "retries": 3
                                },
                                "timestamp": "2025-10-18T16:02:26.568169539"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Plantilla de evaluación no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/module/{id}")
    public ResponseEntity<ApiResponse<List<AssessmentTemplateResponse>>> getAssessmentTemplateByModuleId(@PathVariable Long id){
        List<AssessmentTemplate> assessmentTemplates = assessmentTemplateService.getAssessmentsByModuleId(id);

        if(assessmentTemplates.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<AssessmentTemplateResponse> responses = assessmentTemplates.stream().map(assessmentTemplateWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Evaluciones encontradas correspondientes al modulo: " +id, responses));
    }

    @Operation(
        summary = "Obtener evaluaciones por id",
        description = "Busca y devuelve una plantilla de evalución por su id en especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la plantilla de evaluación",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Plantilla evaluación encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Evaluciones encontradas correspondientes al modulo: 1",
                                "data": [
                                    {
                                        "id": 1,
                                        "type": "QUIZ",
                                        "retries": 3
                                    },
                                    {
                                        "id": 2,
                                        "type": "WORKSHOP",
                                        "retries": 1
                                    },
                                    {
                                        "id": 3,
                                        "type": "FINAL_ASSESSMENT",
                                        "retries": 1
                                    },
                                    {
                                        "id": 4,
                                        "type": "QUIZ",
                                        "retries": 3
                                    },
                                    {
                                        "id": 5,
                                        "type": "WORKSHOP",
                                        "retries": 1
                                    },
                                    {
                                        "id": 6,
                                        "type": "FINAL_ASSESSMENT",
                                        "retries": 1
                                    }
                                ],
                                "timestamp": "2025-10-18T16:07:06.193336228"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Plantilla de evaluación no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentTemplateResponse>> getAssessmentTemplateById(@PathVariable Long id){
        AssessmentTemplate assessmentTemplate = assessmentTemplateService.findById(id).get();
        AssessmentTemplateResponse assessmentTemplateResponse = assessmentTemplateWebMapper.domainToResponse(assessmentTemplate);
        return ResponseEntity.ok(ApiResponse.success("AssesmentTemplate obtenida correctamente", assessmentTemplateResponse));
    }

    @Operation(
        summary = "Obtener plantillas de evaluaciones por id de feedback ",
        description = "Busca y devuelve una plantilla de evalución por el id de un feedback especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del feedback",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Plantilla de evaluación encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "FeedBack obtenido correctamente",
                                "data": [
                                    {
                                        "id": 3,
                                        "question": "What is a microservice?",
                                        "answer": "An architecture pattern"
                                    },
                                    {
                                        "id": 4,
                                        "question": "What is a microservice?",
                                        "answer": "An architecture pattern"
                                    },
                                    {
                                        "id": 5,
                                        "question": "What is a microservice?",
                                        "answer": "An architecture pattern"
                                    }
                                ],
                                "timestamp": "2025-10-18T16:12:32.157766829"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Plantilla de evaluación no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/feedback/{id}")
    public ResponseEntity<ApiResponse<List<FeedBackResponse>>> getFeedback(@PathVariable Long id){
        List<FeedBack> feedBack = assessmentTemplateService.getFeedbackById(id);
        if(feedBack.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<FeedBackResponse> response = feedBack.stream().map(feedBackWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("FeedBack obtenido correctamente", response));
    }
}