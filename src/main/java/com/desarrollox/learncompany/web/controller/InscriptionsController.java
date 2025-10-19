package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.domain.service.IInscriptionService;
import com.desarrollox.learncompany.web.dto.InscriptionRequest;
import com.desarrollox.learncompany.web.dto.InscriptionResponse;
import com.desarrollox.learncompany.web.webMapper.InscriptionWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
@Tag(
    name = "Inscriptions",
    description = "Endpoints para la gestión de inscripciones de empleados en cursos dentro de la organización."
)
public class InscriptionsController {
    
    private final IInscriptionService inscriptionService;
    private final InscriptionWebMapper inscriptionWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Operation(
        summary = "Crear una nueva inscripción",
        description = "Permite registrar una nueva inscripción de un empleado en un curso.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "employeeId": 2,
                            "courseId": 5
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Inscripción creada correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Inscripcion creada correctamente",
                                "data": {
                                    "id": 1,
                                    "employeeId": 1,
                                    "dateAndHour": "2025-10-18T15:23:39.750960352",
                                    "courseId": 1,
                                    "status": "IN_PROGRESS"
                                },
                                "timestamp": "2025-10-18T15:23:39.766021441"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado o curso no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Ya existe una inscripción del empleado en el curso", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<InscriptionResponse>> createInscription(@Valid @RequestBody InscriptionRequest request) {
        loggingService.logInfo("Iniciando creación de Inscription para employeeId: {} y courseId: {}", 
                request != null && request.getEmployeeId() != null ? request.getEmployeeId() : "null", 
                request != null && request.getCourseId() != null ? request.getCourseId() : "null");
        try {
            Inscription inscription = inscriptionWebMapper.requestToDomain(request);
            Inscription inscriptionSaved = inscriptionService.createInscription(inscription);
            InscriptionResponse response = inscriptionWebMapper.domainToResponse(inscriptionSaved);
            loggingService.logInfo("Inscription creada exitosamente con ID: {} para employeeId: {} y courseId: {}", 
                    inscriptionSaved.getId(), inscriptionSaved.getEmployee().getId(), inscriptionSaved.getCourse().getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Inscripcion creada correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Inscription para employeeId: {} y courseId: {}: {}", 
                    request != null && request.getEmployeeId() != null ? request.getEmployeeId() : "null", 
                    request != null && request.getCourseId() != null ? request.getCourseId() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener inscripción por ID",
        description = "Devuelve la información detallada de una inscripción según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la inscripción",
                required = true,
                example = "10"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Inscripción encontrada",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Inscripcion encontrada",
                                "data": {
                                    "id": 1,
                                    "employeeId": 1,
                                    "dateAndHour": "2025-10-18T15:23:39",
                                    "courseId": 1,
                                    "status": "IN_PROGRESS"
                                },
                                "timestamp": "2025-10-18T15:26:00.36592405"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> getInscriptionById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Inscription con ID: {}", id);
        try {
            Inscription inscription = inscriptionService.getInscriptionById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Inscription con ID " + id + " no encontrada"));
            InscriptionResponse inscriptionResponse = inscriptionWebMapper.domainToResponse(inscription);
            loggingService.logInfo("Inscription ID {} obtenida exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Inscripcion encontrada", inscriptionResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Inscription ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Eliminar una inscripción",
        description = "Elimina una inscripción existente según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la inscripción a eliminar",
                required = true,
                example = "10"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Inscripción eliminada correctamente",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Inscripcion encontrada",
                                "data": {
                                    "id": 1,
                                    "employeeId": 1,
                                    "dateAndHour": "2025-10-18T15:23:39",
                                    "courseId": 1,
                                    "status": "IN_PROGRESS"
                                },
                                "timestamp": "2025-10-18T15:26:00.36592405"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> deleteInscription(@PathVariable Long id) {
        loggingService.logInfo("Iniciando eliminación de Inscription con ID: {}", id);
        try {
            Inscription inscriptionDeleted = inscriptionService.deleteInscription(id)
                    .orElseThrow(() -> new IllegalArgumentException("Inscription con ID " + id + " no encontrada"));
            InscriptionResponse inscriptionResponse = inscriptionWebMapper.domainToResponse(inscriptionDeleted);
            loggingService.logInfo("Inscription ID {} eliminada exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Inscripcion eliminada correctamente", inscriptionResponse));
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Inscription ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener inscripciones por curso",
        description = "Devuelve la lista de inscripciones asociadas a un curso específico.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del curso",
                required = true,
                example = "5"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Inscripciones encontradas para el curso especificado",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Obtenidas las inscripciones pertenecientes al curso: 5",
                                "data": [
                                    {
                                        "id": 1,
                                        "employeeId": 2,
                                        "dateAndHour": "2025-10-18T15:23:39",
                                        "courseId": 5,
                                        "status": "IN_PROGRESS"
                                    },
                                    {
                                        "id": 2,
                                        "employeeId": 6,
                                        "dateAndHour": "2025-10-18T15:23:39",
                                        "courseId": 5,
                                        "status": "IN_PROGRESS"
                                    }
                                ],
                                "timestamp": "2025-10-18T10:35:50.200Z"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado para filtrar", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/course/{id}")
    public ResponseEntity<ApiResponse<List<InscriptionResponse>>> getInscriptionsByCourseById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Inscriptions para courseId: {}", id);
        try {
            List<Inscription> inscriptions = inscriptionService.findByCourseId(id);

            if (inscriptions.isEmpty()) {
                loggingService.logWarning("No se encontraron Inscriptions para courseId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<InscriptionResponse> inscriptionResponses = inscriptions.stream()
                    .map(inscriptionWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Inscriptions para courseId: {}", inscriptionResponses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Obtenidas las inscripciones pertenecientes al curso: " + id, inscriptionResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Inscriptions para courseId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener inscripciones por empleado",
        description = "Devuelve la lista de inscripciones asociadas a un empleado específico.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del empleado",
                required = true,
                example = "5"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Inscripciones encontradas para el empleado especificado",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Obtenidas las inscripciones pertenecientes al empleado: 5",
                                "data": [
                                    {
                                        "id": 1,
                                        "employeeId": 5,
                                        "dateAndHour": "2025-10-18T15:23:39",
                                        "courseId": 2,
                                        "status": "IN_PROGRESS"
                                    },
                                    {
                                        "id": 2,
                                        "employeeId": 5,
                                        "dateAndHour": "2025-10-18T15:23:39",
                                        "courseId": 3,
                                        "status": "IN_PROGRESS"
                                    }
                                ],
                                "timestamp": "2025-10-18T10:35:50.200Z"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado no encontrado para filtrar", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/employee/{id}")
    public ResponseEntity<ApiResponse<List<InscriptionResponse>>> getInscriptionsByEmployeeById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Inscriptions para employeeId: {}", id);
        try {
            List<Inscription> inscriptions = inscriptionService.findByEmployeeId(id);

            if (inscriptions.isEmpty()) {
                loggingService.logWarning("No se encontraron Inscriptions para employeeId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<InscriptionResponse> inscriptionResponses = inscriptions.stream()
                    .map(inscriptionWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Inscriptions para employeeId: {}", inscriptionResponses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Obtenidas las inscripciones pertenecientes al empleado: " + id, inscriptionResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Inscriptions para employeeId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}