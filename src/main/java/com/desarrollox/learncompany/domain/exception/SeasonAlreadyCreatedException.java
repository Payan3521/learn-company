package com.desarrollox.learncompany.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SeasonAlreadyCreatedException extends RuntimeException{

    public SeasonAlreadyCreatedException() {
        super("Ya existe una temporada creada");
    }

    public SeasonAlreadyCreatedException(Long id) {
        super("La temporada con ID " + id + " ya ha sido creada");
    }

    public SeasonAlreadyCreatedException(String message) {
        super(message);
    }

}