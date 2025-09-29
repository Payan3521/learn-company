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
import com.desarrollox.learncompany.domain.service.IUserService;
import com.desarrollox.learncompany.web.dto.EmployeeRequest;
import com.desarrollox.learncompany.web.dto.InstructorRequest;
import com.desarrollox.learncompany.web.dto.UserResponse;
import com.desarrollox.learncompany.web.webMapper.EmployeeWebMapper;
import com.desarrollox.learncompany.web.webMapper.InstructorWebMapper;
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
    private final InstructorWebMapper instructorWebMapper;
    private final LoggingService loggingService;

    @PostMapping("/employee")
    public ResponseEntity<ApiResponse<UserResponse>> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        //mapear de request a dominio
        loggingService.logInfo("Creando usuario", request.getName());
        loggingService.logDebug("GSSSSSSSSSSSSSAAGGGA", request.getDepartmentId());
        Employee employee = employeeWebMapper.requestToDomain(request);
        //lamar al servicio y mandarle el dominio
        Employee employeeSaved = userService.createEmployee(employee);
        //mapear de dominio a response
        UserResponse userResponse = userWebMapper.employeeToResponse(employeeSaved);
        //retornar con api response
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Empleado registrado correctamente", userResponse));
    }

    @PostMapping("/instructor")
    public ResponseEntity<ApiResponse<UserResponse>> createInstructor(@Valid @RequestBody InstructorRequest request) {
        //mapear de request a dominio
        Instructor instructor = instructorWebMapper.requestToDomain(request);
        //lamar al servicio y mandarle el dominio
        Instructor employeeSaved = userService.createInstructor(instructor);
        //mapear de dominio a response
        UserResponse userResponse = userWebMapper.instructorToResponse(employeeSaved);
        //retornar con api response
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Instructor registrado correctamente", userResponse));
        
    }

    @PostMapping("/assingn-badge")
    public ResponseEntity<?> assignBadge(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUsersById(@PathVariable Long id){
        User user = userService.findById(id).get();
        UserResponse userResponse = userWebMapper.userToResponse(user);
        return ResponseEntity.ok(ApiResponse.success("Usuario encontrado", userResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        List<User> users = userService.findAll();
        List<UserResponse> userResponses = users.stream().map(userWebMapper::userToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Usuarios encontrados", userResponses));
    }

    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getByUsersFilters(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String role,
            @RequestParam(required = false, defaultValue = "true") boolean status
        ){
        List<User> users = userService.findUsersByFilters(departmentId, role, status);
        List<UserResponse> userResponses = users.stream().map(userWebMapper::userToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Usuarios encontrados", userResponses));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(){
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
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable Long id){
        User userDeleted = userService.delete(id).get();
        UserResponse userResponse = userWebMapper.userToResponse(userDeleted);
        return ResponseEntity.ok(ApiResponse.success("Usuario eliminado correctamente", userResponse));
    }
}