package com.desarrollox.learncompany.web.dto;

import com.desarrollox.learncompany.domain.model.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstructorRequest {
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastname;

    @NotNull(message = "El rol es obligatorio")
    private Role role;

    @NotNull(message = "El departamento es obligatorio")
    //validacion para que sea mayor a 0 el id
    private Long departmentId;

    @NotNull(message = "La url de la foto es obligatoria")
    private String urlPhoto;

    private String specialty;
    
    private String biography;
}