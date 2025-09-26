package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModulesController {
    
    @PostMapping
    public ResponseEntity<?> createModule(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModuleById(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllModules(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/assessments/{id}")
    public ResponseEntity<?> getAssessmentsById(){
        throw new IllegalArgumentException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteModule(){
        throw new IllegalArgumentException();
    }

}