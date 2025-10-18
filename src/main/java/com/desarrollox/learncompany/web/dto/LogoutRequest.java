package com.desarrollox.learncompany.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogoutRequest {
    @NotBlank(message = "Refresh token es requerido")
    private String refreshToken;
}