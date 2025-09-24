package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CertificatesController {
    
    public ResponseEntity<?> createCertificate(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getAllNotifications(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getUsersById(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> getCertificatesById(){
        throw new IllegalArgumentException();
    }
}
