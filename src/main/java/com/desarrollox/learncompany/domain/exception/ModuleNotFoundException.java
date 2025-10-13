package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException(Long id) {
        super("Modulo con id: " + id + " no encontrado");
    }

    public ModuleNotFoundException() {
        super("Modulo no encontrado");
    }

    public ModuleNotFoundException(String message) {
        super(message);
    }
    
}