package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthenticationController {
    
    public ResponseEntity<?> login(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> logout(){
        throw new IllegalArgumentException();
    }

    public ResponseEntity<?> refresh(){
        throw new IllegalArgumentException();
    }
}
