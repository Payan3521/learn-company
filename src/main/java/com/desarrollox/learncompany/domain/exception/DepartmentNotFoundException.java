package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Long id) {
        super("Departamento con id: " + id + " no encontrado");
    }

    public DepartmentNotFoundException() {
        super("Departamento no encontrado");
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }
    
}