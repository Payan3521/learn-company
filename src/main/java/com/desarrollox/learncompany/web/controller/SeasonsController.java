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
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.domain.service.ISeasonService;
import com.desarrollox.learncompany.web.dto.SeasonRequest;
import com.desarrollox.learncompany.web.dto.SeasonResponse;
import com.desarrollox.learncompany.web.webMapper.SeasonWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
@Tag(
    name = "Seasons",
    description = "Endpoints para la gestión de temporadas de cursos dentro de la organización."
)
public class SeasonsController {

    private final ISeasonService seasonService;
    private final SeasonWebMapper seasonWebMapper;
    private final LoggingService loggingService;

    @Operation(
        summary = "Crear una nueva temporada",
        description = "Permite registrar una nueva temporada para la organización de cursos.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "duration": 720,
                            "name": "Temporada Primavera 2025"
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Temporada creada correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Temporada creada correctamente",
                                "data": {
                                    "id": 1,
                                    "duration": 720,
                                    "name": "Temporada Primavera 2025",
                                    "courses": []
                                },
                                "timestamp": "2025-10-18T15:40:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<SeasonResponse>> createSeason(@Valid @RequestBody SeasonRequest request) {
        loggingService.logInfo("Iniciando creación de Season con nombre: {}", 
                request != null && request.getName() != null ? request.getName() : "null");
        try {
            Season season = seasonWebMapper.requestToDomain(request);
            Season seasonSaved = seasonService.creatSeason(season);
            SeasonResponse response = seasonWebMapper.domainToResponse(seasonSaved);
            loggingService.logInfo("Season creada exitosamente con ID: {} y nombre: {}", 
                    seasonSaved.getId(), seasonSaved.getName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Temporada creada correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Season con nombre: {}: {}", 
                    request != null && request.getName() != null ? request.getName() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener todas las temporadas",
        description = "Devuelve la lista completa de temporadas registradas en el sistema.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Temporadas encontradas",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Temporadas encontradas",
                                "data": [
                                    {
                                        "id": 1,
                                        "duration": 720,
                                        "name": "Temporada Primavera 2025",
                                        "courses": []
                                    },
                                    {
                                        "id": 2,
                                        "duration": 672,
                                        "name": "Temporada Verano 2025",
                                        "courses": []
                                    }
                                ],
                                "timestamp": "2025-10-18T15:40:00.123456789"
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
    public ResponseEntity<ApiResponse<List<SeasonResponse>>> getAllSeansos() {
        loggingService.logInfo("Obteniendo todas las Seasons");
        try {
            List<Season> seasons = seasonService.getAllSeasons();

            if (seasons.isEmpty()) {
                loggingService.logWarning("No se encontraron Seasons");
                return ResponseEntity.noContent().build();
            }

            List<SeasonResponse> seasonsResponses = seasons.stream()
                    .map(seasonWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Seasons", seasonsResponses.size());
            return ResponseEntity.ok(ApiResponse.success("Temporadas encontradas", seasonsResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todas las Seasons: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener temporada por ID",
        description = "Devuelve la información detallada de una temporada según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la temporada",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Temporada encontrada correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "temporada encontrada correctamente",
                                "data": {
                                    "id": 1,
                                    "duration": 720,
                                    "name": "Temporada Primavera 2025",
                                    "courses": []
                                },
                                "timestamp": "2025-10-18T15:40:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Temporada no encontrada", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeasonResponse>> getSeasonById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Season con ID: {}", id);
        try {
            Season season = seasonService.getSeasonById(id).get();
            SeasonResponse response = seasonWebMapper.domainToResponse(season);
            loggingService.logInfo("Season ID {} obtenida exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("temporada encontrada correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Season ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}