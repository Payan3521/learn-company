package com.desarrollox.learncompany.domain.exception;

public class InscriptionAlreadyRegisteredException extends RuntimeException{
    public InscriptionAlreadyRegisteredException(String message){
        super(message);
    }
    public InscriptionAlreadyRegisteredException(Long idEmployee, Long idCourse){
        super("empleado con id: "+idEmployee +"ya registrado en el curso: " +idCourse );
    }
    public InscriptionAlreadyRegisteredException(){
        super("Empleado ya registrado en el curso");    
    }
}
