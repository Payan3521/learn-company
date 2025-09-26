package com.desarrollox.learncompany.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.service.IUserService;
import com.desarrollox.learncompany.web.dto.EmployeeRequest;
import com.desarrollox.learncompany.web.dto.UserResponse;
import com.desarrollox.learncompany.web.webMapper.EmployeeWebMapper;
import com.desarrollox.learncompany.web.webMapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final IUserService userService;
    private final EmployeeWebMapper employeeWebMapper;
    private final UserWebMapper userWebMapper;
    
    @PostMapping("/employee")
    public ResponseEntity<ApiResponse<UserResponse>> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        //mapear de request a dominio
        Employee employee = employeeWebMapper.requestToDomain(request);
        //lamar al servicio y mandarle el dominio
        Employee employeeSaved = userService.createEmployee(employee);
        //mapear de dominio a response
        UserResponse userResponse = userWebMapper.employeeToResponse(employeeSaved);
        //retornar con api response
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Empleado registrado correctamente", userResponse));
    }

    @PostMapping("/instructor")
    public ResponseEntity<?> createInstructor(){
        throw new IllegalArgumentException();
    }

    @PostMapping("/assingn-badge")
    public ResponseEntity<?> assignBadge(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsersById(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/filters")
    public ResponseEntity<?> getByUsersFilters(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<?> getCertificatesById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<?> getInscriptionsById(){
        throw new IllegalArgumentException();
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(){
        throw new IllegalArgumentException();
    }

    @PutMapping("/instructor/{id}")
    public ResponseEntity<?> updateInstructor(){
        throw new IllegalArgumentException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(){
        throw new IllegalArgumentException();
    }
}