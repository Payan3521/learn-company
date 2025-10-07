package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssessmentTemplateNotFoundException extends RuntimeException{

    public AssessmentTemplateNotFoundException(Long id){
        super("Evalucion template con id: " +id + "no encontrada");
    }

    public AssessmentTemplateNotFoundException(String message){
        super(message);
    }

    public AssessmentTemplateNotFoundException(){
        super("Evalucion template no encontrada");
    }

}