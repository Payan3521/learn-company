package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CoursesController {
    
    @PostMapping
    public ResponseEntity<?> createCourse(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoursesById(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getCoursesByFilters(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/enrolled/{id}")
    public ResponseEntity<?> getEnrolledById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/finishs/{id}")
    public ResponseEntity<?> getFinishById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/optionals/{department}")
    public ResponseEntity<?> getOptionalsByDepartament(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/mandatorys/{departament}")
    public ResponseEntity<?> getMandatorysByDepartamet(){
        throw new IllegalArgumentException();
    }

    @GetMapping("modules/{id}")
    public ResponseEntity<?> getModulesById(){
        throw new IllegalArgumentException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(){
        throw new IllegalArgumentException();
    }
}