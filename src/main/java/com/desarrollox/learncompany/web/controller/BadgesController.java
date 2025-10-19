package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.domain.service.IBadgeService;
import com.desarrollox.learncompany.web.dto.BadgeRequest;
import com.desarrollox.learncompany.web.dto.BadgeResponse;
import com.desarrollox.learncompany.web.webMapper.BadgeWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgesController {

    private final IBadgeService badgeService;
    private final BadgeWebMapper badgeWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    
    @Operation(
        summary = "Crear una nueva insignia",
        description = "Permite a los instructores crear una nueva insignia.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo solicitud",
                    value = """
                        {
                            "name": "novato",
                            "urlIcon": "https://link.com",
                            "criteria": "tener mas de 500 puntos"
                        }
                        """
                )
            )
        ),
        responses =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Insignia creada exitosamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitoso",
                        value = """
                            { 
                                "success": true,
                                "message": "Badge creado correctamente",
                                "data": {
                                    "id": 1,
                                    "name": "novato",
                                    "urlIcon": "https://link.com",
                                    "criteria": "tener mas de 500 puntos"
                                },
                                "timestamp": "2025-10-18T16:24:33.625055993"
                                }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Objeto ya registrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<BadgeResponse>> createBadge(@Valid @RequestBody BadgeRequest request) {
        loggingService.logInfo("Iniciando creación de Badge con nombre: {}", 
                truncateName(request != null && request.getName() != null ? request.getName() : "null"));
        try {
            Badge badge = badgeWebMapper.requestToDomain(request);
            Badge badgeSaved = badgeService.createBadge(badge);
            BadgeResponse response = badgeWebMapper.domainToResponse(badgeSaved);
            loggingService.logInfo("Badge creado exitosamente con ID: {} y nombre: {}", 
                    badgeSaved.getId(), truncateName(badgeSaved.getName()));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Badge creado correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Badge con nombre {}: {}", 
                    truncateName(request != null && request.getName() != null ? request.getName() : "null"), 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener todas las insignias",
        description = "Devuelve la lista completa de inisginias registradas.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Badges encontrados",
                                "data": [
                                    {
                                        "id": 1,
                                        "name": "novato",
                                        "urlIcon": "https://link.com",
                                        "criteria": "tener mas de 500 puntos"
                                    },
                                    {
                                        "id": 2,
                                        "name": "maestro",
                                        "urlIcon": "https://link.com",
                                        "criteria": "tener mas de 100 puntos"
                                    }
                                ],
                                "timestamp": "2025-10-18T16:32:17.243501871"
                            }
                            
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<BadgeResponse>>> getAllBadges() {
        loggingService.logInfo("Obteniendo todos los Badges");
        try {
            List<Badge> badges = badgeService.getAllBadges();

            if (badges.isEmpty()) {
                loggingService.logWarning("No se encontraron Badges");
                return ResponseEntity.noContent().build();
            }

            List<BadgeResponse> badgeResponses = badges.stream()
                    .map(badgeWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Badges", badgeResponses.size());
            return ResponseEntity.ok(ApiResponse.success("Badges encontrados", badgeResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Badges: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener insignias por id",
        description = "Busca y devuelve una insignia por su id en especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la insignia",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insignia encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Badge encontrado ",
                                "data": {
                                    id": 1,
                                    "name": "novato",
                                    "urlIcon": "https://link.com",
                                    "criteria": "tener mas de 500 puntos"
                                },
                                "timestamp": "2025-10-18T16:40:51.812894344"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Insignia no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgesById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Badge con ID: {}", id);
        try {
            Badge badge = badgeService.getBadgeById(id).get();
            BadgeResponse badgeResponse = badgeWebMapper.domainToResponse(badge);
            loggingService.logInfo("Badge ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Badge encontrado", badgeResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener insignias por id de empleado",
        description = "Busca y devuelve una insignia por el id de un empleado especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del empleado",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insignia encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Badge correspondientes al empleado: 2",
                                "data": [
                                    {
                                        "id": 1,
                                        "name": "novato",
                                        "urlIcon": "https://link.com",
                                        "criteria": "tener mas de 500 puntos"
                                    }
                                ],
                                "timestamp": "2025-10-18T16:47:28.98608727"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/employee/{id}")
    public ResponseEntity<ApiResponse<List<BadgeResponse>>> getBadgesByEmployeeId(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Badges para employeeId: {}", id);
        try {
            List<Badge> badges = badgeService.getBadgesByEmployeeId(id);

            if (badges.isEmpty()) {
                loggingService.logWarning("No se encontraron Badges para employeeId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<BadgeResponse> badgeResponses = badges.stream()
                    .map(badgeWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Badges para employeeId: {}", badgeResponses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Badge correspondientes al empleado: " + id, badgeResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badges para employeeId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener insignias por nombre",
        description = "Busca y devuelve una insignia por su nombre en especifico",
        parameters = {
            @Parameter(
                name = "name",
                description = "Nombre de la insignia",
                required = true,
                example = "Maestro"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insignia encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Badge encontrado",
                                "data": {
                                    "id": 1,
                                    "name": "novato",
                                    "urlIcon": "https://link.com",
                                    "criteria": "tener mas de 500 puntos"
                                },
                                "timestamp": "2025-10-18T16:51:32.689910939"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Insignia no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse<BadgeResponse>> getBadgeByName(@RequestParam(required = true) String name) {
        loggingService.logInfo("Obteniendo Badge con nombre: {}", truncateName(name));
        try {
            Badge badge = badgeService.findByName(name).get();
            BadgeResponse badgeResponse = badgeWebMapper.domainToResponse(badge);
            loggingService.logInfo("Badge con nombre {} obtenido exitosamente", truncateName(name));
            return ResponseEntity.ok(ApiResponse.success("Badge encontrado", badgeResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Badge con nombre {}: {}", truncateName(name), e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar nombres de insignias en los logs
    private String truncateName(String name) {
        if (name == null) {
            return "null";
        }
        return name.length() > 30 ? name.substring(0, 30) + "..." : name;
    }
}