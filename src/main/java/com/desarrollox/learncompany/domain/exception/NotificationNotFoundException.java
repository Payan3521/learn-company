package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super("Notificacion con id: " + id + " no encontrada");
    }

    public NotificationNotFoundException() {
        super("Notificacion no encontrada");
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }
    
}