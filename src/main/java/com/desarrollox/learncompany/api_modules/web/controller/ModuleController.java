package com.desarrollox.learncompany.api_modules.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_modules.web.dto.ModuleRequest;
import com.desarrollox.learncompany.api_modules.web.dto.ModuleResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    @PostMapping("/create-module")
    public ResponseEntity<ApiResponse<ModuleResponse>> createModule(
        @RequestBody ModuleRequest moduleRequest){

            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Modulo registrado correctamente", null));
    }

    @GetMapping("/getModuleById/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> getByIdModule(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Modulo encontrado", null));
    }

    @GetMapping("/evaluations-getById/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> getByIdEvaluations(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Evaluation encontrada", null));
    }

    @DeleteMapping("/delete-module/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> deleteModule(@PathVariable Long id){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Modulo eliminado", null));
    }


}
