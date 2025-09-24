package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class InscriptionsController {
    
    public ResponseEntity<?> createInscription(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getInscriptionById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> deleteInscription(){
        throw new IllegalArgumentException();
    }
}
