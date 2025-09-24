package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class DepartmentsController {
    
    public ResponseEntity<?> createDepartament(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAllDepartaments(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getDepartamentsById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getDepartamentsByFilters(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> updateDepartament(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> deleteDepartament(){
        throw new IllegalArgumentException();
    }
}
