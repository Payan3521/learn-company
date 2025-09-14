package com.desarrollox.learncompany.api_stats.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_stats.web.dto.StatsResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    @GetMapping("/analysis/{id}")
    public ResponseEntity<ApiResponse<StatsResponse>> getStatsById(@PathVariable Long id){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Estadistica encontrada", null));

    } 
}