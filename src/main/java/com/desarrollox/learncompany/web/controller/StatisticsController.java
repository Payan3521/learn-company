package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Statistic;
import com.desarrollox.learncompany.domain.service.IStatisticService;
import com.desarrollox.learncompany.web.dto.StatisticResponse;
import com.desarrollox.learncompany.web.webMapper.StatisticWebMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final IStatisticService statisticService;
    private final StatisticWebMapper statisticWebMapper;
    
    @GetMapping
    public ResponseEntity<ApiResponse<StatisticResponse>> getStatistics(){
        Statistic statistic = statisticService.getStatistic()
        .orElseThrow(() -> new RuntimeException("No se pudieron obtener las estadísticas"));

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

        return ResponseEntity.ok(ApiResponse.success(message, response));
    }
}