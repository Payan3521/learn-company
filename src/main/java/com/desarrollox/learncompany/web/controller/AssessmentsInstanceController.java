package com.desarrollox.learncompany.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.domain.service.IAssessmentInstanceService;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceRequest;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceResponse;
import com.desarrollox.learncompany.web.webMapper.AssessmentInstanceWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessmentInstance")
@RequiredArgsConstructor
public class AssessmentsInstanceController {

    private final IAssessmentInstanceService assessmentInstanceService;
    private final AssessmentInstanceWebMapper assessmentInstanceWebMapper;
    
    @Operation(
        summary = "Crear una nueva evaluación",
        description = "Permite a los instructores crear una nueva evaluación.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo solicitud",
                    value = """
                        {
                            "assessmentTemplateId": 5,
                            "employeeId": 2,
                            "answers": [
                                {
                                "content": "mi respuesta es:...",
                                "questionId": 5
                                }
                            ]
                        }
                        """
                )
            )
        ),
        responses =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Evaluación creada exitosamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitoso",
                        value = """
                            { 
                                "success": true,
                                "message": "Evaluacion creada correctamente",
                                "data": {
                                    "id": 1,
                                    "assessmentTemplateId": 4,
                                    "employeeId": 2,
                                    "grade": 0.0,
                                    "status": "PENDING",
                                    "answers": [
                                        {
                                            "id": 1,
                                            "content": "mi respuesta es...",
                                            "questionId": 5,
                                            "dateIssued": "2025-10-18T14:47:18.036127717",
                                            "assessmentInstanceId": 1
                                        }
                                    ]
                                    "createdAt": "2025-10-18T14:47:18.033689351"
                                },
                                "timestamp": "2025-10-18T14:47:18.045651783"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> createAssessmentInstance(@Valid @RequestBody AssessmentInstanceRequest request){
        AssessmentInstance assessmentInstance = assessmentInstanceWebMapper.requestToDomain(request);
        AssessmentInstance assessmentInstanceSaved = assessmentInstanceService.createAssessmentInstance(assessmentInstance);
        AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstanceSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Evaluacion creada correctamente", response));
    }
   
    @Operation(
        summary = "Obtener la nota de la evalución",
        description = "Busca y devuelve la nota de la evalución",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la nota",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nota de evalución encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Nota obtenida correctamente",
                                "data": 0.0,
                                "timestamp": "2025-10-18T15:22:44.364315235"
                            }    
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Evaluación no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/grade/{id}")
    public ResponseEntity<ApiResponse<Double>> getGradeById(@PathVariable Long id){
        Double grade = assessmentInstanceService.getGrade(id);
        return ResponseEntity.ok(ApiResponse.success("Nota obtenida correctamente", grade));
    }
   
    @Operation(
        summary = "Obtener evaluaciones por id",
        description = "Busca y devuelve una evalución por su id en especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la evaluación",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Evaluación encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Instancia de evaluacion obtenida correctamente",
                                "data": {
                                    "id": 1,
                                    "assessmentTemplateId": 4,
                                    "employeeId": 2,
                                    "grade": 0.0,
                                    "status": "PENDING",
                                    "answers": [
                                        {
                                            "id": 1,
                                            "content": "mi respuesta es...",
                                            "questionId": 5,
                                            "dateIssued": "2025-10-18T14:47:18",
                                            "assessmentInstanceId": 1
                                        }
                                    ],
                                    "createdAt": "2025-10-18T14:47:18"
                                },
                                "timestamp": "2025-10-18T15:09:27.64208038"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Modulo no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> getAssessmentsById(@PathVariable Long id){
        AssessmentInstance assessmentInstance = assessmentInstanceService.getAssessmentInstanceById(id).get();
        AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstance);
        return ResponseEntity.ok(ApiResponse.success("Instancia de evaluacion obtenida correctamente", response));
    }
   
    @Operation(
        summary = "Buscar y asignar nota a evaluación",
        description = "Busca y asigna nota a evalucion",
        parameters ={
            @Parameter(
                name = "grade",
                description = "Valor de la nota",
                required = true,
                example = "5.0"
            ),
            @Parameter(
                name = "assessmentId",
                description = "id de la evalución a la cual se le va a asignar la nota",
                required = true,
                example = "1"
                )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nota asignada correctamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Nota asignada correctamente",
                                "data": {
                                    "id": 1,
                                    "assessmentTemplateId": 4,
                                    "employeeId": 2,
                                    "grade": 4.5,
                                    "status": "PENDING",
                                    "answers": [
                                        {
                                            "id": 1,
                                            "content": "mi respuesta es...",
                                            "questionId": 5,
                                            "dateIssued": "2025-10-18T14:47:18",
                                            "assessmentInstanceId": 1
                                        }
                                    ],
                                    "createdAt": "2025-10-18T14:47:18"
                                },
                                "timestamp": "2025-10-18T15:44:08.543682318"
                            }        
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objento no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @PatchMapping("/assign-grade/{grade}/{assessmentId}")
    public ResponseEntity<ApiResponse<AssessmentInstanceResponse>> asignarGrade(@PathVariable Double grade, @PathVariable Long assessmentId){
        AssessmentInstance assessmentInstance = assessmentInstanceService.assignGrade( assessmentId, grade).get();
        AssessmentInstanceResponse response = assessmentInstanceWebMapper.domainToResponse(assessmentInstance);
        return ResponseEntity.ok(ApiResponse.success("Nota asignada correctamente", response));
    }

}