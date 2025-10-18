package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Login;
import com.desarrollox.learncompany.domain.service.IAuthenticationService;
import com.desarrollox.learncompany.web.dto.LoginRequest;
import com.desarrollox.learncompany.web.dto.LoginResponse;
import com.desarrollox.learncompany.web.dto.LogoutRequest;
import com.desarrollox.learncompany.web.dto.RefreshTokenRequest;
import com.desarrollox.learncompany.web.webMapper.AuthenticationWebMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
    private final AuthenticationWebMapper authenticationWebMapper;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request, 
            HttpServletRequest httpServletRequest) {
        
        String ipAddress = getClientIpAddress(httpServletRequest);
        String userAgent = httpServletRequest.getHeader("User-Agent");

        Login login = authenticationService.authenticate(
            request.getEmail(), 
            request.getPassword(), 
            ipAddress, 
            userAgent
        );
        
        LoginResponse response = authenticationWebMapper.toResponse(login);
        return ResponseEntity.ok(ApiResponse.success("Login exitoso", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody LogoutRequest request) {
        authenticationService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("Logout exitoso", null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        Login login = authenticationService.refreshToken(request.getRefreshToken());
        LoginResponse response = authenticationWebMapper.toResponse(login);
        return ResponseEntity.ok(ApiResponse.success("Token renovado exitosamente", response));
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}