package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InscriptionNotFoundException extends RuntimeException {

    public InscriptionNotFoundException(Long id) {
        super("Inscription con id: " + id + " no encontrada");
    }

    public InscriptionNotFoundException() {
        super("Inscription no encontrada");
    }

    public InscriptionNotFoundException(String message) {
        super(message);
    }
    
}