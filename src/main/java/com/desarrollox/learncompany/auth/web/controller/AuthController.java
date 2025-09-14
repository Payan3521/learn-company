package com.desarrollox.learncompany.auth.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.auth.web.dto.LoginRequest;
import com.desarrollox.learncompany.auth.web.dto.LoginResponse;
import com.desarrollox.learncompany.auth.web.dto.LogoutRequest;
import com.desarrollox.learncompany.auth.web.dto.RefreshTokenRequest;
import com.desarrollox.learncompany.auth.web.webMapper.AuthWebMapper;
import com.desarrollox.learncompany.common.web.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthWebMapper authWebMapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authWebMapper.toLoginResponse(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(RefreshTokenRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Logout successful"));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(LogoutRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully"));
    }
}