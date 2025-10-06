package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InstructorUpdateRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String lastname;
    
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    @NotNull(message = "El departamento es obligatorio")
    private Long departmentId;
    
    @NotBlank(message = "La URL de la foto es obligatoria")
    private String urlPhoto;
    
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 2, max = 200, message = "La especialidad debe tener entre 2 y 200 caracteres")
    private String specialty;
    
    @NotBlank(message = "La biografía es obligatoria")
    @Size(min = 10, message = "La biografía debe tener al menos 10 caracteres")
    private String biography;
}
