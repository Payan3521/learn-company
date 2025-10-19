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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
    private final AuthenticationWebMapper authenticationWebMapper;
  
    @Operation(
        summary = "Crear un nuevo login",
        description = "Permite a los usuarios hacer nuevo login",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo solicitud",
                    value = """
                        {
                            "email":"juan@gmail.com",
                            "password":"password123!"
                        }
                        """
                )
            )
        ),
        responses =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Login exitoso",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitoso",
                        value = """
                            { 
                                "success": true,
                                "message": "Login exitoso",
                                "data": {
                                    "accessToken": "placeholder_access_token_juan@gmail.com",
                                    "refreshToken": "placeholder_refresh_token_1760835999117",
                                    "tokenType": "Bearer",
                                    "expiresIn": 3600,
                                    "user": {
                                        "id": 1,
                                        "email": "juan@gmail.com",
                                        "password": "$2a$10$dqyiLDQ7tR7V8355zOcIQOnOFf.IkHf2eut8uxzBBfiPCnFILlyDy",
                                        "name": "name",
                                        "lastname": "lastname",
                                        "status": true,
                                        "role": "EMPLOYEE",
                                        "departmentId": 1,
                                        "urlPhoto": "https://tasks.google.com/tasks",
                                        "puntos": 0
                                    },
                                    "scope": "read write"
                                },
                                "timestamp": "2025-10-18T20:06:39.15597201"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
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