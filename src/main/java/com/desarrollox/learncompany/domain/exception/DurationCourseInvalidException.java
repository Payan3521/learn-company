package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DurationCourseInvalidException extends RuntimeException{
    public DurationCourseInvalidException(){
        super("La duración del curso no puede exceder la duración de la temporada actual");
    }

    public DurationCourseInvalidException(String message){
        super(message);
    }
}