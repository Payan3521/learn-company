package com.desarrollox.learncompany.persistence.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAuthentication;
import com.desarrollox.learncompany.domain.model.LoginAttempt;
import com.desarrollox.learncompany.domain.model.RefreshToken;
import com.desarrollox.learncompany.persistence.mapper.AuthenticationMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryLoginAttempt;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryRefreshToken;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryAuthentication implements IRepositoryAuthentication {

    private final JpaRepositoryLoginAttempt jpaRepositoryLoginAttempt;
    private final JpaRepositoryRefreshToken jpaRepositoryRefreshToken;
    private final AuthenticationMapper authenticationMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    @Transactional
    public void saveRefreshToken(RefreshToken refreshToken) {
        loggingService.logInfo("Iniciando guardado de RefreshToken para userEmail: {}", refreshToken.getUserEmail());
        try {
            jpaRepositoryRefreshToken.save(authenticationMapper.toEntity(refreshToken));
            loggingService.logInfo("RefreshToken guardado exitosamente para userEmail: {}", refreshToken.getUserEmail());
        } catch (Exception e) {
            loggingService.logError("Error al guardar RefreshToken para userEmail {}: {}", 
                    refreshToken.getUserEmail(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void saveLogin(LoginAttempt loginAttempt) {
        loggingService.logInfo("Iniciando guardado de LoginAttempt para userEmail: {}", loginAttempt.getEmail());
        try {
            jpaRepositoryLoginAttempt.save(authenticationMapper.toEntity(loginAttempt));
            loggingService.logInfo("LoginAttempt guardado exitosamente para userEmail: {}", loginAttempt.getEmail());
        } catch (Exception e) {
            loggingService.logError("Error al guardar LoginAttempt para userEmail {}: {}", 
                    loginAttempt.getEmail(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String refreshTokenValue) {
        loggingService.logInfo("Obteniendo RefreshToken por token (truncado): {}", 
                truncateToken(refreshTokenValue));
        try {
            Optional<RefreshToken> token = jpaRepositoryRefreshToken.findByToken(refreshTokenValue)
                    .map(authenticationMapper::toModel);
            if (token.isPresent()) {
                loggingService.logInfo("RefreshToken encontrado para userEmail: {}", token.get().getUserEmail());
            } else {
                loggingService.logWarning("No se encontró RefreshToken para token (truncado): {}", 
                        truncateToken(refreshTokenValue));
            }
            return token;
        } catch (Exception e) {
            loggingService.logError("Error al obtener RefreshToken para token (truncado) {}: {}", 
                    truncateToken(refreshTokenValue), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefreshToken> findActiveTokensByUserEmail(String userEmail) {
        loggingService.logInfo("Obteniendo RefreshTokens activos para userEmail: {}", userEmail);
        try {
            List<RefreshToken> tokens = jpaRepositoryRefreshToken.findByUserEmailAndRevokedFalse(userEmail)
                    .stream()
                    .map(authenticationMapper::toModel)
                    .collect(Collectors.toList());
            if (tokens.isEmpty()) {
                loggingService.logWarning("No se encontraron RefreshTokens activos para userEmail: {}", userEmail);
            } else {
                loggingService.logInfo("Se encontraron {} RefreshTokens activos para userEmail: {}", 
                        tokens.size(), userEmail);
            }
            return tokens;
        } catch (Exception e) {
            loggingService.logError("Error al obtener RefreshTokens activos para userEmail {}: {}", 
                    userEmail, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void revokeAllTokensByUserEmail(String userEmail) {
        loggingService.logInfo("Iniciando revocación de todos los RefreshTokens para userEmail: {}", userEmail);
        try {
            jpaRepositoryRefreshToken.revokeAllByUserEmail(userEmail, LocalDateTime.now());
            loggingService.logInfo("Todos los RefreshTokens revocados exitosamente para userEmail: {}", userEmail);
        } catch (Exception e) {
            loggingService.logError("Error al revocar todos los RefreshTokens para userEmail {}: {}", 
                    userEmail, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void revokeAllTokensExceptCurrent(String userEmail, String currentToken) {
        loggingService.logInfo("Iniciando revocación de todos los RefreshTokens excepto el actual para userEmail: {}", userEmail);
        try {
            jpaRepositoryRefreshToken.revokeAllExceptCurrent(userEmail, currentToken, LocalDateTime.now());
            loggingService.logInfo("RefreshTokens revocados exitosamente para userEmail: {}, excepto token (truncado): {}", 
                    userEmail, truncateToken(currentToken));
        } catch (Exception e) {
            loggingService.logError("Error al revocar RefreshTokens para userEmail {}, excepto token (truncado) {}: {}", 
                    userEmail, truncateToken(currentToken), e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar tokens en los logs y evitar exponer información sensible
    private String truncateToken(String token) {
        if (token == null) {
            return "null";
        }
        return token.length() > 8 ? token.substring(0, 8) + "..." : token;
    }
}