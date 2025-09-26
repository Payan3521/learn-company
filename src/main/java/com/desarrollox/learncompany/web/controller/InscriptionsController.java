package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
public class InscriptionsController {
    
    @PostMapping
    public ResponseEntity<?> createInscription(){
        throw new IllegalArgumentException();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getInscriptionById(){
        throw new IllegalArgumentException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInscription(){
        throw new IllegalArgumentException();
    }
}