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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentsController {

    private final IDepartmentService departmentService;
    private final DepartmentWebMapper departmentWebMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        Department department = departmentWebMapper.requestToDomain(request);
        Department departmentSaved = departmentService.createDepartment(department);
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Departamento creado correctamente", departmentResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(@PathVariable Long id){
        Department department = departmentService.getDepartmentById(id).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(department);
        return ResponseEntity.ok(ApiResponse.success("Departamento encontrado", departmentResponse));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentUpdateRequest request){
        Department department = departmentWebMapper.updateRequestToDomain(request);
        Department departmentUpdated = departmentService.updateDepartment(id, department).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentUpdated);
        return ResponseEntity.ok(ApiResponse.success("Departamento actualizado correctamente", departmentResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> deleteDepartment(@PathVariable Long id){
        Department departmentDeleted = departmentService.deleteDepartment(id).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentDeleted);
        return ResponseEntity.ok(ApiResponse.success("Departamento eliminado correctamente", departmentResponse));
    }
}