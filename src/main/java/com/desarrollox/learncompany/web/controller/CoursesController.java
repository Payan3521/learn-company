package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CoursesController {
    
    public ResponseEntity<?> createCourse(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAllCourses(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getCoursesById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getCoursesByFilters(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getEnrolledById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getFinishById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getOptionalsByDepartament(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getMandatorysByDepartamet(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getModulesById(){
        throw new IllegalArgumentException();
    }
}
