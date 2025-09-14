package com.desarrollox.learncompany.api_badges.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.desarrollox.learncompany.api_badges.web.dto.BadgeRequest;
import com.desarrollox.learncompany.api_badges.web.dto.BadgeResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BadgeResponse>> asignarBadge(
        @RequestBody BadgeRequest badgeRequest){

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Insignia creada", null));
    } 

    @GetMapping
    public ResponseEntity <ApiResponse<BadgeResponse>> getByFilters(@RequestParam String nombre){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Insignia encontrado por filtros", null));
    }
    
    @GetMapping("/getAllBadges")
    public ResponseEntity<ApiResponse<BadgeResponse>> getAllBadges(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Insignias encontrados", null));
    }

    @GetMapping("/getBadgeById/{id}")
    public ResponseEntity<ApiResponse<BadgeResponse>> getByIdBadge(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Insignia encontrado", null));
    }

}
