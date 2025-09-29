package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import com.desarrollox.learncompany.web.webMapper.DepartmentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departaments")
@RequiredArgsConstructor
public class DepartmentsController {

    private final IDepartmentService departmentService;
    private final DepartmentWebMapper departmentWebMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartament(@Valid @RequestBody DepartmentRequest request){
        Department department = departmentWebMapper.requestToDomain(request);
        Department departmentSaved = departmentService.createDepartment(department);
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentSaved);
        return ResponseEntity.ok(ApiResponse.success("Departamento creado correctamente", departmentResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartaments(){
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentResponse> departmentResponses = departments.stream()
            .map(departmentWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Departamentos encontrados", departmentResponses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartamentsById(@PathVariable Long id){
        Department department = departmentService.getDepartmentById(id).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(department);
        return ResponseEntity.ok(ApiResponse.success("Departamento encontrado", departmentResponse));
    }

    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getDepartamentsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) int hierarchy
        ){
        List<Department> departments = departmentService.findDepartmentsByFilters(name, hierarchy);
        List<DepartmentResponse> departmentResponses = departments.stream()
            .map(departmentWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Departamentos encontrados", departmentResponses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartament(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request){
        Department department = departmentWebMapper.requestToDomain(request);
        Department departmentUpdated = departmentService.updateDepartment(id, department).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentUpdated);
        return ResponseEntity.ok(ApiResponse.success("Departamento actualizado correctamente", departmentResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> deleteDepartament(@PathVariable Long id){
        Department departmentDeleted = departmentService.deleteDepartment(id).get();
        DepartmentResponse departmentResponse = departmentWebMapper.domainToResponse(departmentDeleted);
        return ResponseEntity.ok(ApiResponse.success("Departamento eliminado correctamente", departmentResponse));
    }
}