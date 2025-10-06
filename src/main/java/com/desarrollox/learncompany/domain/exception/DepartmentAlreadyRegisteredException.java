package com.desarrollox.learncompany.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DepartmentAlreadyRegisteredException extends RuntimeException {

    public DepartmentAlreadyRegisteredException(String name) {
        super("El departamento con nombre: " + name + " ya está registrado");
    }

    public DepartmentAlreadyRegisteredException() {
        super("El departamento ya está registrado");
    }

}