package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadgeNotFoundException extends RuntimeException {

    public BadgeNotFoundException(Long id) {
        super("Badge con id: " + id + " no encontrado");
    }

    public BadgeNotFoundException() {
        super("Badge no encontrado");
    }
    
    public BadgeNotFoundException(String message) {
        super(message);
    }
    
}