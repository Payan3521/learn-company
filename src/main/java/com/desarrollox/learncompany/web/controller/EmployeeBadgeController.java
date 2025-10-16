package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.domain.service.IEmployeeBadgeService;
import com.desarrollox.learncompany.web.dto.AssignBadgeRequest;
import com.desarrollox.learncompany.web.dto.EmployeeBadgeResponse;
import com.desarrollox.learncompany.web.webMapper.EmployeeBadgeWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/employeeBadge")
@RequiredArgsConstructor
@Tag(
    name = "Employee Badges",
    description = "Endpoints para la gestión de insignias asignadas a empleados dentro de la organización."
)
public class EmployeeBadgeController {

    private final IEmployeeBadgeService employeeBadgeService;
    private final EmployeeBadgeWebMapper employeeBadgeWebMapper;

    @Operation(
        summary = "Asignar una insignia a un empleado",
        description = "Permite asignar una insignia existente a un empleado específico.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de asignación de insignia",
                    value = """
                    {
                      "employeeId": 3,
                      "badgeId": 2,
                      "dateAssigned": "2025-10-15"
                    }
                    """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Insignia asignada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado o insignia no encontrados", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> assignBadgeToEmployee(@Valid @RequestBody AssignBadgeRequest request){
        EmployeeBadge employeeBadge = employeeBadgeWebMapper.requestToDomain(request);
        EmployeeBadge employeeBadgeSaved = employeeBadgeService.assignBadgeToEmployee(employeeBadge);
        EmployeeBadgeResponse response = employeeBadgeWebMapper.domainToResponse(employeeBadgeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("insignia asignada correctamente", response));
    }

    @Operation(
        summary = "Eliminar una insignia asignada a un empleado",
        description = "Elimina la relación entre un empleado y una insignia asignada.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insignia eliminada correctamente del empleado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Relación de insignia y empleado no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> delete(@PathVariable Long id){
        EmployeeBadge employeeeBadgeDeleted = employeeBadgeService.delete(id).get();
        EmployeeBadgeResponse employeeBadgeResponse = employeeBadgeWebMapper.domainToResponse(employeeeBadgeDeleted);
        return ResponseEntity.ok(ApiResponse.success("Insignia eliminada a empleado correctamente", employeeBadgeResponse));
    }

    @Operation(
        summary = "Obtener una insignia asignada por ID",
        description = "Obtiene la información de una relación específica entre empleado e insignia.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Relación encontrada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Relación no encontrada", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> findById(@PathVariable Long id){
        EmployeeBadge employeeBadge = employeeBadgeService.findById(id).get();
        EmployeeBadgeResponse employeeBadgeResponse = employeeBadgeWebMapper.domainToResponse(employeeBadge);
        return ResponseEntity.ok(ApiResponse.success("isignia de empleado obtenido correctamente", employeeBadgeResponse));

    }

    @Operation(
        summary = "Obtener todas las insignias asignadas a empleados",
        description = "Devuelve una lista con todas las insignias asignadas a empleados registrados.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de insignias de empleados obtenida correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "No hay insignias asignadas a empleados", content = @Content)
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeBadgeResponse>>> findAll(){
        List<EmployeeBadge> employeeBadges = employeeBadgeService.findAll();

        if(employeeBadges.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<EmployeeBadgeResponse> employeeBadgeResponses = employeeBadges.stream().map(employeeBadgeWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("insignias encontradas", employeeBadgeResponses));
    }
}