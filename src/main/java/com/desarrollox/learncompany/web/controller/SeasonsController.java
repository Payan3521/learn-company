package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.domain.service.ISeasonService;
import com.desarrollox.learncompany.web.dto.SeasonRequest;
import com.desarrollox.learncompany.web.dto.SeasonResponse;
import com.desarrollox.learncompany.web.webMapper.SeasonWebMapper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeasonsController {

    private final ISeasonService seasonService;
    private final SeasonWebMapper seasonWebMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<SeasonResponse>> createSeason(@Valid @RequestBody SeasonRequest request){
        Season season = seasonWebMapper.requestToDomain(request);
        Season seasonSaved = seasonService.creatSeason(season);
        SeasonResponse response = seasonWebMapper.domainToResponse(seasonSaved);
        return ResponseEntity.ok(ApiResponse.success("Temporada creada correctamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeasonResponse>>> getAllSeansos(){
        List<Season> seasons = seasonService.getAllSeasons();
        List<SeasonResponse> seasonsResponses = seasons.stream().map(seasonWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Temporadas encontradas", seasonsResponses));
    }


    @GetMapping("/courses/{id}")
    public ResponseEntity<ApiResponse<SeasonResponse>> getCoursesById(@PathVariable Long id){
        Season season = seasonService.getSeasonById(id).get();
        SeasonResponse response = seasonWebMapper.domainToResponse(season);
        return ResponseEntity.ok(ApiResponse.success("Curso encontrado correctamente", response));
    }
}