package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ModulesController {
    
    public ResponseEntity<?> createModule(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getModulesById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAssessmentsById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> deleteModule(){
        throw new IllegalArgumentException();
    }

}