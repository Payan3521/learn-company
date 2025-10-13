package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SeasonNotFoundException extends RuntimeException{
    
    public SeasonNotFoundException(Long id){
        super("la temporada con id: " + id + " no fue encontrada");
    }

    public SeasonNotFoundException(String message){
        super(message);
    }

    public SeasonNotFoundException(){
        super("Temporada no encontrada");
    }
}