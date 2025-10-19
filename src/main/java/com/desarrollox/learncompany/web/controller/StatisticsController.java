package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Statistic;
import com.desarrollox.learncompany.domain.service.IStatisticService;
import com.desarrollox.learncompany.web.dto.StatisticResponse;
import com.desarrollox.learncompany.web.webMapper.StatisticWebMapper;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(
    name = "Statistics",
    description = "Endpoints para consultar estadísticas de cursos dentro de la organización."
)
public class StatisticsController {

    private final IStatisticService statisticService;
    private final StatisticWebMapper statisticWebMapper;
    private final LoggingService loggingService;

    @Operation(
        summary = "Obtener estadísticas de cursos",
        description = "Devuelve estadísticas sobre los cursos más y menos tomados en el sistema.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Estadísticas obtenidas correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "📊 Estadísticas de cursos:\\n✅ Curso más tomado: 'Spring Boot Avanzado' con 45 inscripciones.\\n⚠️ Curso menos tomado: 'Introducción a Kotlin' con 3 inscripciones.",
                                "data": {
                                    "topCourseId": 5,
                                    "topCourseName": "Spring Boot Avanzado",
                                    "topCourseInscriptions": 45,
                                    "lessCourseId": 12,
                                    "lessCourseName": "Introducción a Kotlin",
                                    "lessCourseInscriptions": 3
                                },
                                "timestamp": "2025-10-18T15:45:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No hay cursos registrados para generar estadísticas", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<StatisticResponse>> getStatistics() {
        loggingService.logInfo("Obteniendo estadísticas de cursos");
        try {
            Statistic statistic = statisticService.getStatistic()
                    .orElseThrow(() -> new IllegalArgumentException("No hay cursos registrados para generar estadísticas"));
            StatisticResponse response = statisticWebMapper.domainToResponse(statistic);

            String message = String.format(
                "📊 Estadísticas de cursos:\n" +
                "✅ Curso más tomado: '%s' con %d inscripciones.\n" +
                "⚠️ Curso menos tomado: '%s' con %d inscripciones.",
                response.getTopCourseName(),
                response.getTopCourseInscriptions(),
                response.getLessCourseName(),
                response.getLessCourseInscriptions()
            );

            loggingService.logInfo("Estadísticas obtenidas exitosamente: Curso más tomado '{}' con {} inscripciones, Curso menos tomado '{}' con {} inscripciones",
                    response.getTopCourseName(), response.getTopCourseInscriptions(),
                    response.getLessCourseName(), response.getLessCourseInscriptions());
            return ResponseEntity.ok(ApiResponse.success(message, response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener estadísticas de cursos: {}", e.getMessage(), e);
            throw e;
        }
    }
}