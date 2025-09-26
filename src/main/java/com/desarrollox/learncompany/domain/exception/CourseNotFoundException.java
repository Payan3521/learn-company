package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Curso con id: " + id + " no encontrado");
    }

    public CourseNotFoundException() {
        super("Curso no encontrado");
    }
    
    public CourseNotFoundException(String message) {
        super(message);
    }
    
}