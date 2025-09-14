package com.desarrollox.learncompany.api_users.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_badges.web.dto.BadgeRequest;
import com.desarrollox.learncompany.api_badges.web.dto.BadgeResponse;
import com.desarrollox.learncompany.api_certificates.web.dto.CertificateResponse;
import com.desarrollox.learncompany.api_courses.web.dto.CourseResponse;
import com.desarrollox.learncompany.api_inscriptions.web.dto.InscriptionResponse;
import com.desarrollox.learncompany.api_users.web.dto.EmployRequest;
import com.desarrollox.learncompany.api_users.web.dto.EmployResponse;
import com.desarrollox.learncompany.api_users.web.dto.InstructorRequest;
import com.desarrollox.learncompany.api_users.web.dto.InstructorResponse;
import com.desarrollox.learncompany.api_users.web.dto.UserResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ControllerUser {

    @Operation(
        summary = "Registrar un nuevo empleado",
        description = "Permite a un administrador registrar un nuevo empleado en la plataforma.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Empleado registrado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployRequest.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida. Faltan datos o el JSON es incorrecto."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado. Se requiere token JWT."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Prohibido. No tiene permisos para esta operación."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflicto. El correo ya está registrado."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        }
    )
    @PostMapping("/create-employ")
    public ResponseEntity<ApiResponse<EmployResponse>> registrarEmploy(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos para registrar un nuevo empleado.",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de registro",
                    summary = "Ejemplo completo de un empleado",
                    value = """
                    {
                      "email": "ejemplo@gmail.com",
                      "password": "password123!",
                      "nombre": "Juan",
                      "apellido": "Pérez",
                      "rol": "empleado",
                      "departamento": "Recursos Humanos",
                      "urlFoto": "https://miservidor.com/foto.jpg"
                    }
                    """
                )
            )
        )
        @RequestBody EmployRequest employRequest) {
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Empleado registrado correctamente", null));
    }

    @Operation(
        summary = "Registrar un nuevo instructor",
        description = "Permite a un administrador registrar un nuevo instructor en la plataforma.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Instructor registrado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InstructorRequest.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida. Faltan datos o el JSON es incorrecto."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado. Se requiere token JWT."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Prohibido. No tiene permisos para esta operación."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflicto. El correo ya está registrado."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        }
    )
    @PostMapping("/create-instructor")
    public ResponseEntity<ApiResponse<InstructorResponse>> registrarInstructor(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos para registrar un nuevo instructor.",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de registro",
                    summary = "Ejemplo completo de un instructor",
                    value = """
                    {
                        "email": "email@gmail.com",
                        "password": "Password123",
                        "nombre": "nombre",
                        "apellido": "apellido",
                        "rol": "rol",
                        "departamento": "departamento",
                        "urlFoto": "https://miservidor.com/foto.jpg",
                        "especialidad": "especialidad",
                        "biografia": "biografia"
                    }
                    """
                )
            )
        )
        @RequestBody InstructorRequest instructorRequest){

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("instructor registrado correctamente", null));
    }

    @Operation(
        summary = "Asignar una insignia a un usuario",
        description = "Permite a un instructor asignar una insignia a un empleado en la plataforma.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Insignia registrada exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BadgeRequest.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida. Faltan datos o el JSON es incorrecto."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado. Se requiere token JWT."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Prohibido. No tiene permisos para esta operación."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        }
    )
    @PostMapping("/asignar-badge")
    public ResponseEntity<ApiResponse<BadgeResponse>> asignarBadge(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos para asignar una nueva insignia.",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de asignacion",
                    summary = "Ejemplo completo de una insignia",
                    value = """
                    {
                        "nombre": "nombre",
                        "urlIcono": "https://miservidor.com/foto.jpg",
                        "criterio": "criterio" 
                    }
                    """
                )
            )
        )
        @RequestBody BadgeRequest badgeRequest){
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Badge asignado", null));
    } 

    @Operation(
        summary = "Buscar a un usuario por Id",
        description = "Permite buscar un asuario por su Id correspondiente.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Usuario encontrado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida. Faltan datos o el JSON es incorrecto."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado. Se requiere token JWT."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Prohibido. No tiene permisos para esta operación."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        }
    )
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getByIdUser(
        @Parameter Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Usuario encontrado", null));
    } 

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<UserResponse>> getAllUser(){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Usuarios encontrados", null));
    }

    @GetMapping("/getUsersByFilters")
    public ResponseEntity <ApiResponse<UserResponse>> getByFilters(@RequestParam String departamento, @RequestParam boolean estado){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Usuario encontrado por filtros", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMy(){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Perfil encontrado", null));
    } 

    @PutMapping("/update-employ/{id}")
    public ResponseEntity<ApiResponse<EmployResponse>> updateEmploy(@PathVariable Long id, @RequestBody EmployRequest employRequest){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Empleado modificado", null));
    }

    @PutMapping("/update-instructor/{id}")
    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructor(@PathVariable Long id, @RequestBody InstructorRequest instructorRequest){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Instructor modificado", null));
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable Long id){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Usuario eliminado", null));
    }

    @GetMapping("/badges/{id}")
    public ResponseEntity<ApiResponse<BadgeResponse>> getByIdBadge(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Badge encontrado", null));
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<ApiResponse<CertificateResponse>> getByIdCertificates(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Certificado encontrado", null));
    }

    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> getByIdInscriptions(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Inscripcion encontrado", null));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getByIdCourses(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Curso encontrado", null));
    }
}
