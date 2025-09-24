package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class SeasonsController {
    
    public ResponseEntity<?> createSeason(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAllSeansos(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getCoursesById(){
        throw new IllegalArgumentException();
    }
}