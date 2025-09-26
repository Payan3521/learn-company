package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departaments")
@RequiredArgsConstructor
public class DepartmentsController {
    
    @PostMapping
    public ResponseEntity<?> createDepartament(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartaments(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartamentsById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/filters")
    public ResponseEntity<?> getDepartamentsByFilters(){
        throw new IllegalArgumentException();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartament(){
        throw new IllegalArgumentException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartament(){
        throw new IllegalArgumentException();
    }
}
