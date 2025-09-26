package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    @PostMapping
    public ResponseEntity<?> login(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> logout(){
        throw new IllegalArgumentException();
    }

    @GetMapping
    public ResponseEntity<?> refresh(){
        throw new IllegalArgumentException();
    }
}