package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidRoleException extends RuntimeException{
    public InvalidRoleException(String message){
        super(message);
    }

    public InvalidRoleException(){
        super("Rol no soportado");
    }
    
}