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
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.domain.service.IEmployeeBadgeService;
import com.desarrollox.learncompany.web.dto.AssignBadgeRequest;
import com.desarrollox.learncompany.web.dto.EmployeeBadgeResponse;
import com.desarrollox.learncompany.web.webMapper.EmployeeBadgeWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/employeeBadge")
@RequiredArgsConstructor
@Tag(
    name = "Employee Badges",
    description = "Endpoints para la gestión de insignias asignadas a empleados dentro de la organización."
)
public class EmployeeBadgeController {

    private final IEmployeeBadgeService employeeBadgeService;
    private final EmployeeBadgeWebMapper employeeBadgeWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Operation(
        summary = "Asignar nuevo badge a un empleado",
        description = "Permite asignar un badge a un empleado del sistema.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "employeeId":1,
                            "badgeId":1
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201", 
                description = "insignia asignada correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "insignia asignada correctamente",
                                "data": {
                                    "id": 1,
                                    "employeeId": 1,
                                    "badgeId": 1,
                                    "dateEarned": "2025-10-18T14:55:39.300725188"
                                },
                                "timestamp": "2025-10-18T14:55:39.384953526"
                            } 
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Badge ya asignado a empleado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> assignBadgeToEmployee(@Valid @RequestBody AssignBadgeRequest request) {
        loggingService.logInfo("Iniciando asignación de Badge para employeeId: {} y badgeId: {}", 
                request != null && request.getEmployeeId() != null ? request.getEmployeeId() : "null", 
                request != null && request.getBadgeId() != null ? request.getBadgeId() : "null");
        try {
            EmployeeBadge employeeBadge = employeeBadgeWebMapper.requestToDomain(request);
            EmployeeBadge employeeBadgeSaved = employeeBadgeService.assignBadgeToEmployee(employeeBadge);
            EmployeeBadgeResponse response = employeeBadgeWebMapper.domainToResponse(employeeBadgeSaved);
            loggingService.logInfo("Badge asignado exitosamente con ID: {} para employeeId: {} y badgeId: {}", 
                    employeeBadgeSaved.getId(), employeeBadgeSaved.getEmployee().getId(), employeeBadgeSaved.getBadge().getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("insignia asignada correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al asignar Badge para employeeId: {} y badgeId: {}: {}", 
                    request != null && request.getEmployeeId() != null ? request.getEmployeeId() : "null", 
                    request != null && request.getBadgeId() != null ? request.getBadgeId() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Eliminar una asignación de badge",
        description = "Elimina el registro de una asignacion de badge según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del EmployeeBadge a eliminar",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Insignia eliminada a empleado correctamente",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Insignia eliminada a empleado correctamente",
                                "data": {
                                    "id": 1,
                                    "employeeId": 1,
                                    "badgeId": 1,
                                    "dateEarned": "2025-10-18T14:55:39"
                                },
                                "timestamp": "2025-10-18T15:00:56.258509608"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> delete(@PathVariable Long id) {
        loggingService.logInfo("Iniciando eliminación de EmployeeBadge con ID: {}", id);
        try {
            EmployeeBadge employeeBadgeDeleted = employeeBadgeService.delete(id).get();
            EmployeeBadgeResponse employeeBadgeResponse = employeeBadgeWebMapper.domainToResponse(employeeBadgeDeleted);
            loggingService.logInfo("EmployeeBadge ID {} eliminado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Insignia eliminada a empleado correctamente", employeeBadgeResponse));
        } catch (Exception e) {
            loggingService.logError("Error al eliminar EmployeeBadge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener una asignación de badge por ID",
        description = "Busca y devuelve la información de una asignacion de badge según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del EmployeeBadge a buscar",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "insignia de empleado obtenido correctamente",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "insignia de empleado obtenido correctamente",
                                "data": {
                                    "id": 1,
                                    "employeeId": 1,
                                    "badgeId": 1,
                                    "dateEarned": "2025-10-18T14:55:39"
                                },
                                "timestamp": "2025-10-18T14:57:55.236537606"
                            }  
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> findById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo EmployeeBadge con ID: {}", id);
        try {
            EmployeeBadge employeeBadge = employeeBadgeService.findById(id).get();
            EmployeeBadgeResponse employeeBadgeResponse = employeeBadgeWebMapper.domainToResponse(employeeBadge);
            loggingService.logInfo("EmployeeBadge ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("insignia de empleado obtenido correctamente", employeeBadgeResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener EmployeeBadge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener todas las asignaciones de badge",
        description = "Devuelve la lista completa de las asignaciones de badge.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "insignias encontradas",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "insignias encontradas",
                                "data": [
                                    {
                                        "id": 1,
                                        "employeeId": 1,
                                        "badgeId": 1,
                                        "dateEarned": "2025-10-18T14:55:39"
                                    },
                                    {
                                        "id": 2,
                                        "employeeId": 1,
                                        "badgeId": 2,
                                        "dateEarned": "2025-10-18T14:55:39"
                                    },
                                    {
                                        "id": 3,
                                        "employeeId": 1,
                                        "badgeId": 3,
                                        "dateEarned": "2025-10-18T14:55:39"
                                    }
                                ],
                                "timestamp": "2025-10-18T14:59:16.225922105"
                            }   
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacia", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    ) 
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeBadgeResponse>>> findAll() {
        loggingService.logInfo("Obteniendo todas las asignaciones de EmployeeBadge");
        try {
            List<EmployeeBadge> employeeBadges = employeeBadgeService.findAll();

            if (employeeBadges.isEmpty()) {
                loggingService.logWarning("No se encontraron asignaciones de EmployeeBadge");
                return ResponseEntity.noContent().build();
            }

            List<EmployeeBadgeResponse> employeeBadgeResponses = employeeBadges.stream()
                    .map(employeeBadgeWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} asignaciones de EmployeeBadge", employeeBadgeResponses.size());
            return ResponseEntity.ok(ApiResponse.success("insignias encontradas", employeeBadgeResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todas las asignaciones de EmployeeBadge: {}", e.getMessage(), e);
            throw e;
        }
    }
}