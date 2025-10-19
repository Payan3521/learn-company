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
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.service.IDepartmentService;
import com.desarrollox.learncompany.web.dto.DepartmentRequest;
import com.desarrollox.learncompany.web.dto.DepartmentResponse;
import com.desarrollox.learncompany.web.dto.DepartmentUpdateRequest;
import com.desarrollox.learncompany.web.webMapper.DepartmentWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentsController {

    private final IDepartmentService departmentService;
    private final DepartmentWebMapper departmentWebMapper;

    @Operation(
        summary = "Crear un nuevo departamento",
        description = "Permite a los instructores crear un nuevo departamento.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo solicitud",
                    value = """
                        {
                            "name": "Software",
                            "prize": 3,
                            "hierarchy": "3"
                        }
                        """
                )
            )
        ),
        responses =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Departamento creado exitosamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitoso",
                        value = """
                            { 
                                "success": true,
                                "message": "Departamento creado correctamente",
                                "data": {
                                    "id": 2,
                                    "name": "Software",
                                    "prize": "3",
                                    "hierarchy": 3
                                },
                                "timestamp": "2025-10-18T17:53:45.032806252"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Datos ya registrados", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        Department department = departmentWebMapper.requestToDomain(request);
        Department departmentSaved = departmentService.createDepartment(department);
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Departamento creado correctamente", departmentResponse));
    }

    @Operation(
        summary = "Obtener departamento por id",
        description = "Busca y devuelve un departamento por su id en especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de el departamento",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Departamento encontrado",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Departamento encontrado",
                                "data": {
                                    "id": 1,
                                    "name": "nombre_value",
                                    "prize": "3",
                                    "hierarchy": 3
                                },
                                "timestamp": "2025-10-18T18:03:57.254458657"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(@PathVariable Long id){
        Department department = departmentService.getDepartmentById(id).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(department);
        return ResponseEntity.ok(ApiResponse.success("Departamento encontrado", departmentResponse));
    }

    @Operation(
        summary = "Obtener todos los departamentos",
        description = "Devuelve la lista completa de departamentos registrados.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Departamentos encontrados",
                                "data": [
                                    {
                                        "id": 1,
                                        "name": "nombre_value",
                                        "prize": "3",
                                        "hierarchy": 3
                                    },
                                    {
                                        "id": 2,
                                        "name": "Software",
                                        "prize": "3",
                                        "hierarchy": 3
                                    }
                                ],
                                "timestamp": "2025-10-18T18:06:37.225531854"
                            }
                            
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
    
        if (departments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
    
        List<DepartmentResponse> departmentResponses = departments.stream()
                .map(departmentWebMapper::domainToResponse)
                .collect(Collectors.toList());
    
        return ResponseEntity.ok(ApiResponse.success("Departamentos encontrados", departmentResponses));
    }

     @Operation(
        summary = "Buscar y asignar nota a evaluación",
        description = "Busca y asigna nota a evalucion",
        parameters ={
            @Parameter(
                name = "name",
                description = "Nombre del departamento",
                required = true,
                example = "software"
            ),
            @Parameter(
                name = "hierarchy",
                description = "Jerarquia de los departamentos",
                required = true,
                example = "1"
                )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nota asignada correctamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Departamentos encontrados",
                                "data": [
                                    {
                                        "id": 1,
                                        "name": "nombre_value",
                                        "prize": "3",
                                        "hierarchy": 3
                                    },
                                    {
                                        "id": 2,
                                        "name": "Software",
                                        "prize": "3",
                                        "hierarchy": 3
                                    }
                                ],
                                "timestamp": "2025-10-18T18:10:24.040730519"
                            }        
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lsta sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getDepartmentsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") int hierarchy
        ){
        List<Department> departments = departmentService.findDepartmentsByFilters(name, hierarchy);

        if (departments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<DepartmentResponse> departmentResponses = departments.stream()
                .map(departmentWebMapper::domainToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Departamentos encontrados", departmentResponses));
    }

    @Operation(
        summary = "Actualizar un departamento existente",
        description = "Modifica los datos de un departamento según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del departamento a actualizar",
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
                            "name": "ii",
                            "prize": "premio_value",
                            "hierarchy": 4
                        }
                        """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Departamento actualizado correctamente",
                                "data": {
                                    "id": 1,
                                    "name": "ii",
                                    "prize": "premio_value",
                                    "hierarchy": 4
                                },
                                "timestamp": "2025-10-18T18:15:56.737400357"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentUpdateRequest request){
        Department department = departmentWebMapper.updateRequestToDomain(request);
        Department departmentUpdated = departmentService.updateDepartment(id, department).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentUpdated);
        return ResponseEntity.ok(ApiResponse.success("Departamento actualizado correctamente", departmentResponse));
    }

    @Operation(
        summary = "Eliminar un departamento",
        description = "Elimina el registro de un departamento según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del departamento a eliminar",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Departamento eliminado correctamente", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )   
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> deleteDepartment(@PathVariable Long id){
        Department departmentDeleted = departmentService.deleteDepartment(id).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentDeleted);
        return ResponseEntity.ok(ApiResponse.success("Departamento eliminado correctamente", departmentResponse));
    }
}