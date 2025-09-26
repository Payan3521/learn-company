package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssessmentInstanceNotFoundException extends RuntimeException {

    public AssessmentInstanceNotFoundException(Long id) {
        super("Evaluación con id: " + id + " no encontrada");
    }

    public AssessmentInstanceNotFoundException() {
        super("Evaluación no encontrada");
    }
    
    public AssessmentInstanceNotFoundException(String message) {
        super(message);
    }

}