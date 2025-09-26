package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentsController {
    
    @PostMapping
    public ResponseEntity<?> createAssessmentInstance(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/grade/{id}")
    public ResponseEntity<?> getGradeById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssessmentsById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> getFeedbackById(){
        throw new IllegalArgumentException();
    }

    @PostMapping("/assign-grade")
    public ResponseEntity<?> asignarGrade(){
        throw new IllegalArgumentException();
    }

}