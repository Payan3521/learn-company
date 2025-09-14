package com.desarrollox.learncompany.api_certificates.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_certificates.web.dto.CertificateRequest;
import com.desarrollox.learncompany.api_certificates.web.dto.CertificateResponse;
import com.desarrollox.learncompany.api_users.web.dto.UserResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateControl {


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CertificateResponse>> createCertificate(@Valid @RequestBody CertificateRequest certificateRequest){
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Certificado registrada correctamente", null));
    }

    @GetMapping("/getAllCertificates")
    public ResponseEntity<ApiResponse<CertificateResponse>> getAllCertificates(){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Certificados encontrados", null));
    }

    @GetMapping("/userGetById/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getByIdUser(@Parameter Long id){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Usuario encontrado", null));
    } 

    @GetMapping("/getCertificateById/{id}")
    public ResponseEntity<ApiResponse<CertificateResponse>> getByIdCertificate(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Certificado encontrado", null));
    }

    
}
