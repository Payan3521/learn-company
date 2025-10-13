package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeBadgeNotFoundException extends RuntimeException {

    public EmployeeBadgeNotFoundException(Long id) {
        super("EmployeeBadge con id: " + id + " no encontrado");
    }

    public EmployeeBadgeNotFoundException() {
        super("EmployeeBadge no encontrado");
    }
    
    public EmployeeBadgeNotFoundException(String message) {
        super(message);
    }
    
}