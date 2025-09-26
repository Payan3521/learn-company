package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeasonsController {
    
    @PostMapping
    public ResponseEntity<?> createSeason(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllSeansos(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCoursesById(){
        throw new IllegalArgumentException();
    }
}