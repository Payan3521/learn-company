package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeBadgeAlreadyRegisteredException extends RuntimeException{
    public EmployeeBadgeAlreadyRegisteredException(){
        super("Badge ya asignado");
    }
    public EmployeeBadgeAlreadyRegisteredException(String message){
        super(message);
    }
    public EmployeeBadgeAlreadyRegisteredException(Long idEmployee, Long idBadge){
        super("El badge con id: " +idBadge + "ya fue asignado al empleado: " +idEmployee );
    }
}