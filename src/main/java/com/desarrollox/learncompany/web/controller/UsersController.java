package com.desarrollox.learncompany.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    public ResponseEntity<?> createInstructor(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> assignBadge(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getUsersById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAllUsers(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getByUsersFilters(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getMe(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getBadgesById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getCertificatesById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getInscriptionsById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getCoursesById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> updateEmployee(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> updateInstructor(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> deleteUserById(){
        throw new IllegalArgumentException();
    }
}