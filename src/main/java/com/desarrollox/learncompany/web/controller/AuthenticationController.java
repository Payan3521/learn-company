package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.web.dto.LoginRequest;
import com.desarrollox.learncompany.web.dto.LoginResponse;
import com.desarrollox.learncompany.web.dto.LogoutRequest;
import com.desarrollox.learncompany.web.dto.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        throw new IllegalArgumentException();
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<LoginResponse>> logout(@Valid @RequestBody RefreshTokenRequest request){
        throw new IllegalArgumentException();
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refresh(@Valid @RequestBody LogoutRequest logoutRequest){
        throw new IllegalArgumentException();
    }

    //posiblemente validate token
}