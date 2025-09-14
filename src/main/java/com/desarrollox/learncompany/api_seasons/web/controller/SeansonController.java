package com.desarrollox.learncompany.api_seasons.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_courses.web.dto.CourseResponse;
import com.desarrollox.learncompany.api_seasons.web.dto.SeasonRequest;
import com.desarrollox.learncompany.api_seasons.web.dto.SeasonResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeansonController {

    @PostMapping("/create-season")
    public ResponseEntity<ApiResponse<SeasonResponse>> createSeason(
        @RequestBody SeasonRequest seasonRequest){

            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Temporada registrada correctamente", null));
    }

    @GetMapping("/getAllSeasons")
    public ResponseEntity<ApiResponse<SeasonResponse>> getAllSeanson(){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Temporadas encontradas", null));
    }

    @GetMapping("/coursesGetById/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getByIdCourse(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Curso encontrado", null));
    }
    
}
