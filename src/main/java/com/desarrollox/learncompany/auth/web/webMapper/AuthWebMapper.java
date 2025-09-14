package com.desarrollox.learncompany.auth.web.webMapper;

import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.auth.web.dto.LoginRequest;
import com.desarrollox.learncompany.auth.web.dto.LoginResponse;

@Component
public class AuthWebMapper {
        public LoginResponse toLoginResponse(LoginRequest request) {
        return LoginResponse.builder()
            .accessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.simulated_token")
            .refreshToken("refresh_token_example")
            .tokenType("Bearer")
            .expiresIn(3600L) 
            .user(
                LoginResponse.UserInfo.builder()
                    .id(1L)
                    .name("John")   
                    .lastName("Doe") 
                    .email(request.getEmail())
                    .role("employee")
                    .build()
            )
            .build();
    }
}