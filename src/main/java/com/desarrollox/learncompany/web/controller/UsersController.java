package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.IUserService;
import com.desarrollox.learncompany.web.dto.EmployeeRequest;
import com.desarrollox.learncompany.web.dto.EmployeeUpdateRequest;
import com.desarrollox.learncompany.web.dto.InstructorRequest;
import com.desarrollox.learncompany.web.dto.InstructorUpdateRequest;
import com.desarrollox.learncompany.web.dto.UserResponse;
import com.desarrollox.learncompany.web.webMapper.EmployeeWebMapper;
import com.desarrollox.learncompany.web.webMapper.InstructorWebMapper;
import com.desarrollox.learncompany.web.webMapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
    name = "Users",
    description = "Endpoints para la gestión de usuarios (empleados, instructores y administradores) dentro de la organización."
)
public class UsersController {

    private final IUserService userService;
    private final EmployeeWebMapper employeeWebMapper;
    private final UserWebMapper userWebMapper;
    private final InstructorWebMapper instructorWebMapper;
    private final LoggingService loggingService;

    @Operation(
        summary = "Crear un empleado",
        description = "Permite registrar un nuevo empleado en el sistema.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "email": "empleado@example.com",
                            "password": "password123",
                            "name": "Juan",
                            "lastname": "Pérez",
                            "role": "EMPLOYEE",
                            "departmentId": 1,
                            "urlPhoto": "https://example.com/photo.jpg"
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Empleado registrado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Empleado registrado correctamente",
                                "data": {
                                    "id": 1,
                                    "email": "empleado@example.com",
                                    "name": "Juan",
                                    "lastname": "Pérez",
                                    "status": true,
                                    "role": "EMPLOYEE",
                                    "departmentId": 1,
                                    "urlPhoto": "https://example.com/photo.jpg",
                                    "puntos": 0,
                                    "certificates": [],
                                    "inscriptions": []
                                },
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "El email ya está registrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PostMapping("/employee")
    public ResponseEntity<ApiResponse<UserResponse>> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        loggingService.logInfo("Iniciando creación de Employee con email: {}", 
                request != null && request.getEmail() != null ? request.getEmail() : "null");
        try {
            Employee employee = employeeWebMapper.requestToDomain(request);
            Employee employeeSaved = userService.createEmployee(employee);
            UserResponse userResponse = userWebMapper.employeeToResponse(employeeSaved);
            loggingService.logInfo("Employee creado exitosamente con ID: {} y email: {}", 
                    employeeSaved.getId(), employeeSaved.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Empleado registrado correctamente", userResponse));
        } catch (Exception e) {
            loggingService.logError("Error al crear Employee con email: {}: {}", 
                    request != null && request.getEmail() != null ? request.getEmail() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Crear un instructor",
        description = "Permite registrar un nuevo instructor en el sistema.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "email": "instructor@example.com",
                            "password": "password123",
                            "name": "María",
                            "lastname": "González",
                            "role": "INSTRUCTOR",
                            "departmentId": 2,
                            "urlPhoto": "https://example.com/photo.jpg",
                            "specialty": "Desarrollo Web",
                            "biography": "Ingeniera de software con 10 años de experiencia"
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Instructor registrado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Instructor registrado correctamente",
                                "data": {
                                    "id": 2,
                                    "email": "instructor@example.com",
                                    "name": "María",
                                    "lastname": "González",
                                    "status": true,
                                    "role": "INSTRUCTOR",
                                    "departmentId": 2,
                                    "urlPhoto": "https://example.com/photo.jpg",
                                    "specialty": "Desarrollo Web",
                                    "biography": "Ingeniera de software con 10 años de experiencia",
                                    "courses": []
                                },
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "El email ya está registrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PostMapping("/instructor")
    public ResponseEntity<ApiResponse<UserResponse>> createInstructor(@Valid @RequestBody InstructorRequest request) {
        loggingService.logInfo("Iniciando creación de Instructor con email: {}", 
                request != null && request.getEmail() != null ? request.getEmail() : "null");
        try {
            Instructor instructor = instructorWebMapper.requestToDomain(request);
            Instructor instructorSaved = userService.createInstructor(instructor);
            UserResponse userResponse = userWebMapper.instructorToResponse(instructorSaved);
            loggingService.logInfo("Instructor creado exitosamente con ID: {} y email: {}", 
                    instructorSaved.getId(), instructorSaved.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Instructor registrado correctamente", userResponse));
        } catch (Exception e) {
            loggingService.logError("Error al crear Instructor con email: {}: {}", 
                    request != null && request.getEmail() != null ? request.getEmail() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener usuario por ID",
        description = "Devuelve la información detallada de un usuario según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del usuario",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuario encontrado",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Usuario encontrado",
                                "data": {
                                    "id": 1,
                                    "email": "empleado@example.com",
                                    "name": "Juan",
                                    "lastname": "Pérez",
                                    "status": true,
                                    "role": "EMPLOYEE",
                                    "departmentId": 1,
                                    "urlPhoto": "https://example.com/photo.jpg",
                                    "puntos": 0
                                },
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUsersById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo User con ID: {}", id);
        try {
            User user = userService.findById(id).get();
            UserResponse userResponse = userWebMapper.userToResponse(user);
            loggingService.logInfo("User ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Usuario encontrado", userResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Devuelve la lista completa de usuarios registrados en el sistema.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuarios encontrados",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Usuarios encontrados",
                                "data": [
                                    {
                                        "id": 1,
                                        "email": "empleado@example.com",
                                        "name": "Juan",
                                        "lastname": "Pérez",
                                        "status": true,
                                        "role": "EMPLOYEE",
                                        "departmentId": 1,
                                        "urlPhoto": "https://example.com/photo.jpg"
                                    },
                                    {
                                        "id": 2,
                                        "email": "instructor@example.com",
                                        "name": "María",
                                        "lastname": "González",
                                        "status": true,
                                        "role": "INSTRUCTOR",
                                        "departmentId": 2,
                                        "urlPhoto": "https://example.com/photo2.jpg"
                                    }
                                ],
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        loggingService.logInfo("Obteniendo todos los Users");
        try {
            List<User> users = userService.findAll();

            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Users");
                return ResponseEntity.noContent().build();
            }

            List<UserResponse> userResponses = users.stream()
                    .map(userWebMapper::userToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Users", userResponses.size());
            return ResponseEntity.ok(ApiResponse.success("Usuarios encontrados", userResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Users: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener usuarios con filtros",
        description = "Devuelve usuarios filtrados por departamento, rol y/o estado.",
        parameters = {
            @Parameter(
                name = "departmentId",
                description = "Identificador del departamento (opcional)",
                example = "1"
            ),
            @Parameter(
                name = "role",
                description = "Rol del usuario: EMPLOYEE, INSTRUCTOR, ADMIN (opcional)",
                example = "EMPLOYEE"
            ),
            @Parameter(
                name = "status",
                description = "Estado del usuario: true (activo) o false (inactivo). Por defecto: true",
                example = "true"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuarios encontrados",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Usuarios encontrados",
                                "data": [
                                    {
                                        "id": 1,
                                        "email": "empleado@example.com",
                                        "name": "Juan",
                                        "lastname": "Pérez",
                                        "status": true,
                                        "role": "EMPLOYEE",
                                        "departmentId": 1,
                                        "urlPhoto": "https://example.com/photo.jpg"
                                    }
                                ],
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getByUsersFilters(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false, defaultValue = "true") boolean status) {
        loggingService.logInfo("Obteniendo Users con filtros - departmentId: {}, role: {}, status: {}", 
                departmentId != null ? departmentId : "null", role != null ? role : "null", status);
        try {
            List<User> users = userService.findUsersByFilters(departmentId, role, status);

            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Users con filtros - departmentId: {}, role: {}, status: {}", 
                        departmentId != null ? departmentId : "null", role != null ? role : "null", status);
                return ResponseEntity.noContent().build();
            }

            List<UserResponse> userResponses = users.stream()
                    .map(userWebMapper::userToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Users con filtros - departmentId: {}, role: {}, status: {}", 
                    userResponses.size(), departmentId != null ? departmentId : "null", role != null ? role : "null", status);
            return ResponseEntity.ok(ApiResponse.success("Usuarios encontrados", userResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Users con filtros - departmentId: {}, role: {}, status: {}: {}", 
                    departmentId != null ? departmentId : "null", role != null ? role : "null", status, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Actualizar un empleado",
        description = "Actualiza la información de un empleado existente.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del empleado",
                required = true,
                example = "1"
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "name": "Juan Carlos",
                            "lastname": "Pérez López",
                            "email": "juan.perez@example.com",
                            "password": "newpassword123",
                            "departmentId": 2,
                            "urlPhoto": "https://example.com/new-photo.jpg"
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Empleado actualizado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Empleado actualizado correctamente",
                                "data": {
                                    "id": 1,
                                    "email": "juan.perez@example.com",
                                    "name": "Juan Carlos",
                                    "lastname": "Pérez López",
                                    "status": true,
                                    "role": "EMPLOYEE",
                                    "departmentId": 2,
                                    "urlPhoto": "https://example.com/new-photo.jpg"
                                },
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado o departamento no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PutMapping("/employee/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest request) {
        loggingService.logInfo("Iniciando actualización de Employee con ID: {}", id);
        try {
            Employee employee = employeeWebMapper.updateRequestToDomain(request);
            User userUpdated = userService.updateUser(id, employee).get();
            UserResponse userResponse = userWebMapper.employeeToResponse((Employee) userUpdated);
            loggingService.logInfo("Employee ID {} actualizado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Empleado actualizado correctamente", userResponse));
        } catch (Exception e) {
            loggingService.logError("Error al actualizar Employee ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Actualizar un instructor",
        description = "Actualiza la información de un instructor existente.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del instructor",
                required = true,
                example = "2"
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                            "name": "María José",
                            "lastname": "González Martínez",
                            "email": "maria.gonzalez@example.com",
                            "password": "newpassword123",
                            "departmentId": 3,
                            "urlPhoto": "https://example.com/new-photo2.jpg",
                            "specialty": "Desarrollo Full Stack",
                            "biography": "Ingeniera de software con 12 años de experiencia en desarrollo web"
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Instructor actualizado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Instructor actualizado correctamente",
                                "data": {
                                    "id": 2,
                                    "email": "maria.gonzalez@example.com",
                                    "name": "María José",
                                    "lastname": "González Martínez",
                                    "status": true,
                                    "role": "INSTRUCTOR",
                                    "departmentId": 3,
                                    "urlPhoto": "https://example.com/new-photo2.jpg",
                                    "specialty": "Desarrollo Full Stack",
                                    "biography": "Ingeniera de software con 12 años de experiencia en desarrollo web"
                                },
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Instructor o departamento no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @PutMapping("/instructor/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorUpdateRequest request) {
        loggingService.logInfo("Iniciando actualización de Instructor con ID: {}", id);
        try {
            Instructor instructor = instructorWebMapper.updateRequestToDomain(request);
            User userUpdated = userService.updateUser(id, instructor).get();
            UserResponse userResponse = userWebMapper.instructorToResponse((Instructor) userUpdated);
            loggingService.logInfo("Instructor ID {} actualizado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Instructor actualizado correctamente", userResponse));
        } catch (Exception e) {
            loggingService.logError("Error al actualizar Instructor ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Eliminar un usuario",
        description = "Elimina (desactiva) un usuario según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del usuario a eliminar",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuario eliminado correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Usuario eliminado correctamente",
                                "data": {
                                    "id": 1,
                                    "email": "empleado@example.com",
                                    "name": "Juan",
                                    "lastname": "Pérez",
                                    "status": false,
                                    "role": "EMPLOYEE",
                                    "departmentId": 1,
                                    "urlPhoto": "https://example.com/photo.jpg"
                                },
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable Long id) {
        loggingService.logInfo("Iniciando eliminación de User con ID: {}", id);
        try {
            User userDeleted = userService.delete(id).get();
            UserResponse userResponse = userWebMapper.userToResponse(userDeleted);
            loggingService.logInfo("User ID {} eliminado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Usuario eliminado correctamente", userResponse));
        } catch (Exception e) {
            loggingService.logError("Error al eliminar User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener empleados que finalizaron cursos",
        description = "Devuelve la lista de empleados que han completado al menos un curso.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuarios que han finalizado cursos",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Usuarios que han finalizado cursos",
                                "data": [
                                    {
                                        "id": 1,
                                        "email": "empleado@example.com",
                                        "name": "Juan",
                                        "lastname": "Pérez",
                                        "status": true,
                                        "role": "EMPLOYEE",
                                        "departmentId": 1,
                                        "urlPhoto": "https://example.com/photo.jpg",
                                        "puntos": 150
                                    }
                                ],
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/finished")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getEmployeesFinished() {
        loggingService.logInfo("Obteniendo Employees que han finalizado cursos");
        try {
            List<Employee> users = userService.findEmployeesFinished();

            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees que han finalizado cursos");
                return ResponseEntity.noContent().build();
            }

            List<UserResponse> userResponses = users.stream()
                    .map(userWebMapper::employeeToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Employees que han finalizado cursos", userResponses.size());
            return ResponseEntity.ok(ApiResponse.success("Usuarios que han finalizado cursos", userResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Employees que han finalizado cursos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener empleados que finalizaron un curso específico",
        description = "Devuelve la lista de empleados que han completado un curso en particular.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del curso",
                example = "5"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuarios que han finalizado el curso especificado",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Usuarios que han finalizado el curso: 5",
                                "data": [
                                    {
                                        "id": 1,
                                        "email": "empleado@example.com",
                                        "name": "Juan",
                                        "lastname": "Pérez",
                                        "status": true,
                                        "role": "EMPLOYEE",
                                        "departmentId": 1,
                                        "urlPhoto": "https://example.com/photo.jpg",
                                        "puntos": 150
                                    }
                                ],
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado para filtrar", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/finished/{id}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getEmployeesFinishedByIdCourse(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Employees que han finalizado el curso ID: {}", id);
        try {
            List<Employee> users = userService.findEmployeesFinishedByCourseId(id);

            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees que han finalizado el curso ID: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<UserResponse> userResponses = users.stream()
                    .map(userWebMapper::employeeToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Employees que han finalizado el curso ID: {}", userResponses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Usuarios que han finalizado el curso: " + id, userResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Employees que han finalizado el curso ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener ranking de empleados por departamento",
        description = "Devuelve el ranking de empleados ordenados por puntos dentro de un departamento específico.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del departamento",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Ranking obtenido para el departamento especificado",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Ranking obtenido para el departamento: 1",
                                "data": [
                                    {
                                        "id": 1,
                                        "email": "empleado1@example.com",
                                        "name": "Juan",
                                        "lastname": "Pérez",
                                        "status": true,
                                        "role": "EMPLOYEE",
                                        "departmentId": 1,
                                        "urlPhoto": "https://example.com/photo.jpg",
                                        "puntos": 250
                                    },
                                    {
                                        "id": 3,
                                        "email": "empleado2@example.com",
                                        "name": "Carlos",
                                        "lastname": "López",
                                        "status": true,
                                        "role": "EMPLOYEE",
                                        "departmentId": 1,
                                        "urlPhoto": "https://example.com/photo3.jpg",
                                        "puntos": 180
                                    }
                                ],
                                "timestamp": "2025-10-18T15:50:00.123456789"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departamento no encontrado para filtrar", content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content())
        }
    )
    @GetMapping("/ranking/{id}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getRanking(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo ranking de Employees para departmentId: {}", id);
        try {
            List<Employee> users = userService.getRankingByDepartment(id);

            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees para el ranking de departmentId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<UserResponse> responses = users.stream()
                    .map(userWebMapper::employeeToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Employees para el ranking de departmentId: {}", responses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Ranking obtenido para el departamento: " + id, responses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener ranking de Employees para departmentId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}