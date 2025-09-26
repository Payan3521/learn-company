package com.desarrollox.learncompany.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyRegisteredException extends RuntimeException {
    private String email;

    public UserAlreadyRegisteredException(String email) {
        super("El usuario con email " + email + " ya está registrado");
        this.email=email;
    }
}