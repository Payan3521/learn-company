package com.desarrollox.learncompany.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
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
    private final LoggingService loggingService; // Inyectar LoggingService

  
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
        loggingService.logInfo("Iniciando login para email: {}, IP: {}, User-Agent: {}", 
                truncateEmail(request != null && request.getEmail() != null ? request.getEmail() : "null"),
                truncateIpAddress(ipAddress),
                truncateUserAgent(userAgent));
        try {
            Login login = authenticationService.authenticate(
                    request.getEmail(), 
                    request.getPassword(), 
                    ipAddress, 
                    userAgent
            );
            LoginResponse response = authenticationWebMapper.toResponse(login);
            loggingService.logInfo("Login exitoso para email: {}", truncateEmail(request.getEmail()));
            return ResponseEntity.ok(ApiResponse.success("Login exitoso", response));
        } catch (Exception e) {
            loggingService.logError("Error en login para email: {}, IP: {}, User-Agent: {}: {}", 
                    truncateEmail(request != null && request.getEmail() != null ? request.getEmail() : "null"),
                    truncateIpAddress(ipAddress),
                    truncateUserAgent(userAgent),
                    e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody LogoutRequest request) {
        loggingService.logInfo("Iniciando logout para refreshToken: {}", 
                truncateToken(request != null && request.getRefreshToken() != null ? request.getRefreshToken() : "null"));
        try {
            authenticationService.logout(request.getRefreshToken());
            loggingService.logInfo("Logout exitoso para refreshToken: {}", 
                    truncateToken(request.getRefreshToken()));
            return ResponseEntity.ok(ApiResponse.success("Logout exitoso", null));
        } catch (Exception e) {
            loggingService.logError("Error en logout para refreshToken {}: {}", 
                    truncateToken(request != null && request.getRefreshToken() != null ? request.getRefreshToken() : "null"),
                    e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        loggingService.logInfo("Iniciando renovación de token para refreshToken: {}", 
                truncateToken(request != null && request.getRefreshToken() != null ? request.getRefreshToken() : "null"));
        try {
            Login login = authenticationService.refreshToken(request.getRefreshToken());
            LoginResponse response = authenticationWebMapper.toResponse(login);
            loggingService.logInfo("Token renovado exitosamente para refreshToken: {}", 
                    truncateToken(request.getRefreshToken()));
            return ResponseEntity.ok(ApiResponse.success("Token renovado exitosamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al renovar token para refreshToken {}: {}", 
                    truncateToken(request != null && request.getRefreshToken() != null ? request.getRefreshToken() : "null"),
                    e.getMessage(), e);
            throw e;
        }
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

    // Método auxiliar para truncar correos electrónicos en los logs
    private String truncateEmail(String email) {
        if (email == null) {
            return "null";
        }
        int atIndex = email.indexOf('@');
        if (atIndex > 0 && email.length() > 10) {
            return email.substring(0, Math.min(4, atIndex)) + "****" + email.substring(atIndex);
        }
        return email.length() > 10 ? email.substring(0, 10) + "..." : email;
    }

    // Método auxiliar para truncar direcciones IP en los logs
    private String truncateIpAddress(String ipAddress) {
        if (ipAddress == null) {
            return "null";
        }
        // Enmascara los últimos dos octetos para IPv4 o últimos segmentos para IPv6
        if (ipAddress.contains(".")) {
            String[] parts = ipAddress.split("\\.");
            if (parts.length == 4) {
                return parts[0] + "." + parts[1] + ".*.*";
            }
        } else if (ipAddress.contains(":")) {
            return ipAddress.substring(0, Math.min(15, ipAddress.length())) + "...";
        }
        return ipAddress.length() > 15 ? ipAddress.substring(0, 15) + "..." : ipAddress;
    }

    // Método auxiliar para truncar agentes de usuario en los logs
    private String truncateUserAgent(String userAgent) {
        if (userAgent == null) {
            return "null";
        }
        return userAgent.length() > 30 ? userAgent.substring(0, 30) + "..." : userAgent;
    }

    // Método auxiliar para truncar tokens de refresco en los logs
    private String truncateToken(String token) {
        if (token == null) {
            return "null";
        }
        return token.length() > 10 ? token.substring(0, 10) + "..." : token;
    }
}