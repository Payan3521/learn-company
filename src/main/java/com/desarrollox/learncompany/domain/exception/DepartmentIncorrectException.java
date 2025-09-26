package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DepartmentIncorrectException extends RuntimeException {

    public DepartmentIncorrectException(String message) {
        super(message);
    }

    public DepartmentIncorrectException() {
        super("No perteneces al mismo departamento");
    }

    public DepartmentIncorrectException(Long id) {
        super("El departamento con id: " + id + " es incorrecto");
    }
    
}