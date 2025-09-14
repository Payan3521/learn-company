package com.desarrollox.learncompany.api_autentication.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_autentication.web.dto.LoginRequest;
import com.desarrollox.learncompany.api_users.web.dto.UserResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticationController {

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest loginRequest){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Login exitoso", null));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<UserResponse>> logout(){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Logout exitoso", null));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<UserResponse>> refresh(){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("refresh exitoso", null));
    }

    
}
