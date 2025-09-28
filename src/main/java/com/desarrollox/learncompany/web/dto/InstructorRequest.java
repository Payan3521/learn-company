package com.desarrollox.learncompany.web.dto;

import org.hibernate.validator.constraints.URL;
import com.desarrollox.learncompany.domain.model.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
    @Size(max = 255, message = "El email no puede superar los 255 caracteres")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    private String lastname;

    @NotNull(message = "El rol es obligatorio")
    private Role role;

    @NotNull(message = "El ID del departamento es obligatorio")
    @Min(value = 1, message = "El ID del departamento debe ser mayor que 0")
    private Long departmentId;

    @NotBlank(message = "La URL de la foto es obligatoria")
    @Size(max = 500, message = "La URL de la foto no puede superar los 500 caracteres")
    @URL(message = "La URL de la foto debe ser una dirección válida")
    private String urlPhoto;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 255, message = "La especialidad no puede superar los 255 caracteres")
    private String specialty;
    
    @NotBlank(message = "La biografia es obligatoria")
    @Size(max = 1000, message = "La biografía no puede superar los 1000 caracteres")
    private String biography;
}