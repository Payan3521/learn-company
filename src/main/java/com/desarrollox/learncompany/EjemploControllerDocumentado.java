package com.desarrollox.learncompany.web.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee", description = "Gestión de empleados (CRUD)")
public class EjemploControllerDocumentado {

    private final EmployeeService employeeService;

    // ============================================================
    // CREATE
    // ============================================================

    @Operation(
        summary = "Crear un nuevo empleado",
        description = "Permite registrar un nuevo empleado en el sistema.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de solicitud",
                    value = """
                        {
                          "name": "Juan Pérez",
                          "email": "juan.perez@empresa.com",
                          "department": "Recursos Humanos"
                        }
                        """
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                              "id": 1,
                              "name": "Juan Pérez",
                              "email": "juan.perez@empresa.com",
                              "department": "Recursos Humanos"
                            }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El email ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee created = employeeService.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ============================================================
    // READ (ALL)
    // ============================================================

    @Operation(
        summary = "Obtener todos los empleados",
        description = "Devuelve la lista completa de empleados registrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            [
                              {
                                "id": 1,
                                "name": "Juan Pérez",
                                "email": "juan.perez@empresa.com",
                                "department": "Recursos Humanos"
                              },
                              {
                                "id": 2,
                                "name": "Ana Gómez",
                                "email": "ana.gomez@empresa.com",
                                "department": "Marketing"
                              }
                            ]
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // ============================================================
    // READ (BY ID)
    // ============================================================

    @Operation(
        summary = "Obtener un empleado por ID",
        description = "Busca y devuelve la información de un empleado específico.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del empleado",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                              "id": 1,
                              "name": "Juan Pérez",
                              "email": "juan.perez@empresa.com",
                              "department": "Recursos Humanos"
                            }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Operation(
        summary = "Actualizar un empleado existente",
        description = "Modifica los datos de un empleado según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del empleado a actualizar",
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
                          "name": "Juan Pérez Actualizado",
                          "email": "juan.perez@empresa.com",
                          "department": "Finanzas"
                        }
                        """
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                              "id": 1,
                              "name": "Juan Pérez Actualizado",
                              "email": "juan.perez@empresa.com",
                              "department": "Finanzas"
                            }
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.update(id, employee);
        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Operation(
        summary = "Eliminar un empleado",
        description = "Elimina el registro de un empleado según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del empleado a eliminar",
                required = true,
                example = "1"
            )
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
