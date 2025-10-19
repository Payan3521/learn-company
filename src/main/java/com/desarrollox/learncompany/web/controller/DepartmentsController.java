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
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.service.IDepartmentService;
import com.desarrollox.learncompany.web.dto.DepartmentRequest;
import com.desarrollox.learncompany.web.dto.DepartmentResponse;
import com.desarrollox.learncompany.web.dto.DepartmentUpdateRequest;
import com.desarrollox.learncompany.web.webMapper.DepartmentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentsController {

    private final IDepartmentService departmentService;
    private final DepartmentWebMapper departmentWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        loggingService.logInfo("Iniciando creación de Department con nombre: {}", 
                truncateName(request != null && request.getName() != null ? request.getName() : "null"));
        try {
            Department department = departmentWebMapper.requestToDomain(request);
            Department departmentSaved = departmentService.createDepartment(department);
            DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentSaved);
            loggingService.logInfo("Department creado exitosamente con ID: {} y nombre: {}", 
                    departmentSaved.getId(), truncateName(departmentSaved.getName()));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Departamento creado correctamente", departmentResponse));
        } catch (Exception e) {
            loggingService.logError("Error al crear Department con nombre {}: {}", 
                    truncateName(request != null && request.getName() != null ? request.getName() : "null"), 
                    e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Department con ID: {}", id);
        try {
            Department department = departmentService.getDepartmentById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Department con ID " + id + " no encontrado"));
            DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(department);
            loggingService.logInfo("Department ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Departamento encontrado", departmentResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        loggingService.logInfo("Obteniendo todos los Departments");
        try {
            List<Department> departments = departmentService.getAllDepartments();

            if (departments.isEmpty()) {
                loggingService.logWarning("No se encontraron Departments");
                return ResponseEntity.noContent().build();
            }

            List<DepartmentResponse> departmentResponses = departments.stream()
                    .map(departmentWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Departments", departmentResponses.size());
            return ResponseEntity.ok(ApiResponse.success("Departamentos encontrados", departmentResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Departments: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getDepartmentsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") int hierarchy) {
        loggingService.logInfo("Obteniendo Departments con filtros: nombre={}, jerarquía={}", 
                truncateName(name), hierarchy);
        try {
            List<Department> departments = departmentService.findDepartmentsByFilters(name, hierarchy);

            if (departments.isEmpty()) {
                loggingService.logWarning("No se encontraron Departments con filtros: nombre={}, jerarquía={}", 
                        truncateName(name), hierarchy);
                return ResponseEntity.noContent().build();
            }

            List<DepartmentResponse> departmentResponses = departments.stream()
                    .map(departmentWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Departments con filtros: nombre={}, jerarquía={}", 
                    departmentResponses.size(), truncateName(name), hierarchy);
            return ResponseEntity.ok(ApiResponse.success("Departamentos encontrados", departmentResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Departments con filtros: nombre={}, jerarquía={}: {}", 
                    truncateName(name), hierarchy, e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(@PathVariable Long id, 
            @Valid @RequestBody DepartmentUpdateRequest request) {
        loggingService.logInfo("Iniciando actualización de Department con ID: {} y nombre: {}", 
                id, truncateName(request != null && request.getName() != null ? request.getName() : "null"));
        try {
            Department department = departmentWebMapper.updateRequestToDomain(request);
            Department departmentUpdated = departmentService.updateDepartment(id, department)
                    .orElseThrow(() -> new IllegalArgumentException("Department con ID " + id + " no encontrado"));
            DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentUpdated);
            loggingService.logInfo("Department ID {} actualizado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Departamento actualizado correctamente", departmentResponse));
        } catch (Exception e) {
            loggingService.logError("Error al actualizar Department ID {} con nombre {}: {}", 
                    id, truncateName(request != null && request.getName() != null ? request.getName() : "null"), 
                    e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> deleteDepartment(@PathVariable Long id) {
        loggingService.logInfo("Iniciando eliminación de Department con ID: {}", id);
        try {
            Department departmentDeleted = departmentService.deleteDepartment(id)
                    .orElseThrow(() -> new IllegalArgumentException("Department con ID " + id + " no encontrado"));
            DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentDeleted);
            loggingService.logInfo("Department ID {} eliminado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Departamento eliminado correctamente", departmentResponse));
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar nombres de departamentos en los logs
    private String truncateName(String name) {
        if (name == null) {
            return "null";
        }
        return name.length() > 30 ? name.substring(0, 30) + "..." : name;
    }
}