package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificatesController {
    
    @PostMapping
    public ResponseEntity<?> createCertificate(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> getAllCertificates(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUsersById(){
        throw new IllegalArgumentException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCertificatesById(){
        throw new IllegalArgumentException();
    }
}