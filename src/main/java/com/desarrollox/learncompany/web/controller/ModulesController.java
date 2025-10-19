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
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.service.IModuleService;
import com.desarrollox.learncompany.web.dto.ModuleRequest;
import com.desarrollox.learncompany.web.dto.ModuleResponse;
import com.desarrollox.learncompany.web.webMapper.ModuleWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(
    name = "Modules",
    description = "Endpoints para la gestión de módulos de cursos dentro de la organización."
)
public class ModulesController {

    private final IModuleService moduleService;
    private final ModuleWebMapper moduleWebMapper;
    private final LoggingService loggingService;

    @Operation(
        summary = "Crear un nuevo módulo",
        description = "Permite crear un nuevo módulo con sus evaluaciones asociadas para un curso específico.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "courseId": 1,
                            "urlVideo": "https://example.com/video.mp4",
                            "urlGuia": "https://example.com/guia.pdf",
                            "hierarchy": 1,
                            "title": "Introducción al módulo",
                            "assessmentTemplate": [
                                {
                                    "questions": [
                                        {
                                            "question": "¿Qué es Spring Boot?",
                                            "responseOptions": "A) Framework, B) Lenguaje, C) Base de datos",
                                            "correctAnswer": "A) Framework"
                                        }
                                    ],
                                    "type": "QUIZ",
                                    "retries": 3
                                }
                            ]
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Módulo creado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Modulo creado correctamente",
                                "data": {
                                    "id": 1,
                                    "courseId": 1,
                                    "urlVideo": "https://example.com/video.mp4",
                                    "urlGuia": "https://example.com/guia.pdf",
                                    "hierarchy": 1,
                                    "title": "Introducción al módulo",
                                    "assessmentTemplate": [
                                        {
                                            "id": 1,
                                            "moduleId": 1,
                                            "questions": [],
                                            "type": "QUIZ",
                                            "retries": 3
                                        }
                                    ]
                                },
                                "timestamp": "2025-10-18T15:30:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<ModuleResponse>> createModule(@Valid @RequestBody ModuleRequest request) {
        loggingService.logInfo("Iniciando creación de Module para courseId: {} y title: {}", 
                request != null && request.getCourseId() != null ? request.getCourseId() : "null", 
                request != null && request.getTitle() != null ? request.getTitle() : "null");
        try {
            Module module = moduleWebMapper.requestToDomain(request);
            Module createdModule = moduleService.createModule(module);
            ModuleResponse response = moduleWebMapper.domainToResponse(createdModule);
            loggingService.logInfo("Module creado exitosamente con ID: {} para courseId: {}", 
                    createdModule.getId(), createdModule.getCourse().getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Modulo creado correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Module para courseId: {} y title: {}: {}", 
                    request != null && request.getCourseId() != null ? request.getCourseId() : "null", 
                    request != null && request.getTitle() != null ? request.getTitle() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener módulo por ID",
        description = "Devuelve la información detallada de un módulo según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del módulo",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Módulo encontrado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Modulo encontrado correctamente",
                                "data": {
                                    "id": 1,
                                    "courseId": 1,
                                    "urlVideo": "https://example.com/video.mp4",
                                    "urlGuia": "https://example.com/guia.pdf",
                                    "hierarchy": 1,
                                    "title": "Introducción al módulo",
                                    "assessmentTemplate": []
                                },
                                "timestamp": "2025-10-18T15:30:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Módulo no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> getModuleById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Module con ID: {}", id);
        try {
            Module module = moduleService.getModuleById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Module con ID " + id + " no encontrado"));
            ModuleResponse response = moduleWebMapper.domainToResponse(module);
            loggingService.logInfo("Module ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Modulo encontrado correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Module ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener todos los módulos",
        description = "Devuelve la lista completa de módulos registrados en el sistema.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Módulos obtenidos correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Modulos obtenidos correctamente",
                                "data": [
                                    {
                                        "id": 1,
                                        "courseId": 1,
                                        "urlVideo": "https://example.com/video1.mp4",
                                        "urlGuia": "https://example.com/guia1.pdf",
                                        "hierarchy": 1,
                                        "title": "Módulo 1",
                                        "assessmentTemplate": []
                                    },
                                    {
                                        "id": 2,
                                        "courseId": 1,
                                        "urlVideo": "https://example.com/video2.mp4",
                                        "urlGuia": "https://example.com/guia2.pdf",
                                        "hierarchy": 2,
                                        "title": "Módulo 2",
                                        "assessmentTemplate": []
                                    }
                                ],
                                "timestamp": "2025-10-18T15:30:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getAllModules() {
        loggingService.logInfo("Obteniendo todos los Modules");
        try {
            List<Module> modules = moduleService.getAllModules();

            if (modules.isEmpty()) {
                loggingService.logWarning("No se encontraron Modules");
                return ResponseEntity.noContent().build();
            }

            List<ModuleResponse> response = modules.stream()
                    .map(moduleWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Modules", response.size());
            return ResponseEntity.ok(ApiResponse.success("Modulos obtenidos correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Modules: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener módulos por curso",
        description = "Devuelve la lista de módulos asociados a un curso específico.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del curso",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Módulos obtenidos correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Modulos obtenidos correctamente",
                                "data": [
                                    {
                                        "id": 1,
                                        "courseId": 1,
                                        "urlVideo": "https://example.com/video1.mp4",
                                        "urlGuia": "https://example.com/guia1.pdf",
                                        "hierarchy": 1,
                                        "title": "Módulo 1",
                                        "assessmentTemplate": []
                                    },
                                    {
                                        "id": 2,
                                        "courseId": 1,
                                        "urlVideo": "https://example.com/video2.mp4",
                                        "urlGuia": "https://example.com/guia2.pdf",
                                        "hierarchy": 2,
                                        "title": "Módulo 2",
                                        "assessmentTemplate": []
                                    }
                                ],
                                "timestamp": "2025-10-18T15:30:00.123456789"
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
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getModulesByCourseId(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Modules para courseId: {}", id);
        try {
            List<Module> modules = moduleService.getModulesByCourseId(id);

            if (modules.isEmpty()) {
                loggingService.logWarning("No se encontraron Modules para courseId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<ModuleResponse> response = modules.stream()
                    .map(moduleWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Modules para courseId: {}", response.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Modulos obtenidos correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Modules para courseId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Eliminar un módulo",
        description = "Elimina un módulo existente según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del módulo a eliminar",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Módulo eliminado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Modulo eliminado correctamente",
                                "data": {
                                    "id": 1,
                                    "courseId": 1,
                                    "urlVideo": "https://example.com/video.mp4",
                                    "urlGuia": "https://example.com/guia.pdf",
                                    "hierarchy": 1,
                                    "title": "Introducción al módulo",
                                    "assessmentTemplate": []
                                },
                                "timestamp": "2025-10-18T15:30:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Módulo no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> deleteModule(@PathVariable Long id) {
        loggingService.logInfo("Iniciando eliminación de Module con ID: {}", id);
        try {
            Module moduleDeleted = moduleService.deleteModule(id)
                    .orElseThrow(() -> new IllegalArgumentException("Module con ID " + id + " no encontrado"));
            ModuleResponse moduleResponse = moduleWebMapper.domainToResponse(moduleDeleted);
            loggingService.logInfo("Module ID {} eliminado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Modulo eliminado correctamente", moduleResponse));
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Module ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}