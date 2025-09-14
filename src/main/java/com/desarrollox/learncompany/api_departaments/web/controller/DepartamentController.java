package com.desarrollox.learncompany.api_departaments.web.controller;

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
import com.desarrollox.learncompany.api_departaments.web.dto.DepartamentRequest;
import com.desarrollox.learncompany.api_departaments.web.dto.DepartamentResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departaments")
@RequiredArgsConstructor
public class DepartamentController {

    @PostMapping("/create-departament")
    public ResponseEntity<ApiResponse<DepartamentResponse>> createDepartament(
        @RequestBody DepartamentRequest instructorRequest){

            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Departamento registrado correctamente", null));
    }

    @GetMapping("/getAllDepartaments")
    public ResponseEntity<ApiResponse<DepartamentResponse>> getAllDepartament(){
       
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Departamentos encontrados", null));
    }

     @GetMapping("/getDepartamentById/{id}")
    public ResponseEntity<ApiResponse<DepartamentResponse>> getByIdDepartament(@Parameter Long id){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Departamento encontrado", null));
    } 

    @GetMapping("/getDepartamentByFilters")
    public ResponseEntity <ApiResponse<DepartamentResponse>> getByFilters(@RequestParam String name, @RequestParam String hierarchy){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Departamento encontrado por filtros", null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DepartamentResponse>> updateDepartament(@PathVariable Long id, @RequestBody DepartamentRequest departamentRequest){
       
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Departamento modificado", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<DepartamentResponse>> deleteDepartament(@PathVariable Long id){
       
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Departamento eliminado", null));
    }
}
