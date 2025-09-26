package com.desarrollox.learncompany.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class SeasonAlreadyCreatedException extends RuntimeException{

    public SeasonAlreadyCreatedException() {
        super("La temporada ya ha sido creada");
    }

    public SeasonAlreadyCreatedException(Long id) {
        super("La temporada con ID " + id + " ya ha sido creada");
    }

    public SeasonAlreadyCreatedException(String message) {
        super(message);
    }

}