package com.desarrollox.learncompany.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BadgeAlreadyRegisteredException extends RuntimeException{
    
    public BadgeAlreadyRegisteredException(){
        super("Ya existe el badge");
    }

    public BadgeAlreadyRegisteredException(String message){
        super("Ya existe un badge con este nombre: " + message);
    }

}