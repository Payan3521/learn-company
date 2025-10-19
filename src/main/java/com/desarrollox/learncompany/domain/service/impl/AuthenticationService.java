package com.desarrollox.learncompany.domain.service.impl;

import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAuthentication;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.InvalidCredentialsException;
import com.desarrollox.learncompany.domain.exception.InvalidTokenException;
import com.desarrollox.learncompany.domain.exception.UserNotActiveException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Login;
import com.desarrollox.learncompany.domain.model.LoginAttempt;
import com.desarrollox.learncompany.domain.model.RefreshToken;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IRepositoryUser repositoryUser;
    private final IRepositoryAuthentication repositoryAuthentication;
    private final PasswordEncoder passwordEncoder;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Login authenticate(String email, String password, String ipAddress, String userAgent) {
        loggingService.logInfo("Iniciando autenticación para email: {} desde IP: {}", email, ipAddress);
        try {
            if (!repositoryUser.existsByEmail(email)) {
                loggingService.logError("Usuario con email {} no encontrado", email);
                saveFailedLogin(email, ipAddress, userAgent, "Usuario no registrado");
                throw new UserNotFoundException("El usuario con email: " + email + " no existe");
            }

            User user = repositoryUser.findByEmail(email)
                    .orElseThrow(() -> {
                        loggingService.logError("Usuario con email {} no encontrado en búsqueda", email);
                        return new UserNotFoundException("Usuario no encontrado");
                    });

            loggingService.logDebug("Usuario encontrado: email={}, id={}", user.getEmail(), user.getId());

            if (!user.canLogin()) {
                loggingService.logError("Usuario con email {} no activo o no verificado", email);
                saveFailedLogin(email, ipAddress, userAgent, "Usuario no activo o no verificado");
                throw new UserNotActiveException(email);
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                loggingService.logError("Contraseña incorrecta para email: {}", email);
                saveFailedLogin(email, ipAddress, userAgent, "Contraseña incorrecta");
                throw new InvalidCredentialsException(email);
            }

            // Generar access token con JWT
            String accessToken = generateAccessToken(user);
            loggingService.logDebug("Access token generado para email: {}", email);

            // Generar refresh token
            String refreshTokenValue = generateRefreshToken();
            loggingService.logDebug("Refresh token generado para email: {}", email);

            // Revocar todos los refresh tokens anteriores del usuario
            loggingService.logInfo("Revocando todos los refresh tokens previos para email: {}", email);
            repositoryAuthentication.revokeAllTokensByUserEmail(user.getEmail());

            // Guardar nuevo refresh token
            saveRefreshToken(refreshTokenValue, user.getEmail());
            loggingService.logInfo("Refresh token guardado para email: {}", email);

            // Registrar intento exitoso
            saveSuccessfulLogin(email, ipAddress, userAgent);
            loggingService.logInfo("Autenticación exitosa para email: {}", email);

            // Calcular expiresIn basado en la configuración JWT
            Long expiresIn = 3600L; // 1 hora por defecto
            return Login.succes(accessToken, refreshTokenValue, expiresIn, user);
        } catch (Exception e) {
            loggingService.logError("Error durante autenticación para email {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void logout(String refreshTokenValue) {
        loggingService.logInfo("Iniciando logout para refreshToken: {}", refreshTokenValue);
        try {
            RefreshToken refreshToken = repositoryAuthentication.findByToken(refreshTokenValue)
                    .orElseThrow(() -> {
                        loggingService.logError("Refresh token {} no encontrado", refreshTokenValue);
                        return new InvalidTokenException("Refresh token no encontrado");
                    });

            if (refreshToken.isRevoked()) {
                loggingService.logError("Refresh token {} ya fue revocado", refreshTokenValue);
                throw new InvalidTokenException("El refresh token ya fue revocado");
            }

            refreshToken.revoke();
            repositoryAuthentication.saveRefreshToken(refreshToken);
            loggingService.logInfo("Logout exitoso para refreshToken: {}", refreshTokenValue);
        } catch (Exception e) {
            loggingService.logError("Error durante logout para refreshToken {}: {}", refreshTokenValue, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Login refreshToken(String refreshTokenValue) {
        loggingService.logInfo("Iniciando renovación de token para refreshToken: {}", refreshTokenValue);
        try {
            RefreshToken refreshToken = repositoryAuthentication.findByToken(refreshTokenValue)
                    .orElseThrow(() -> {
                        loggingService.logError("Refresh token {} no encontrado", refreshTokenValue);
                        return new InvalidTokenException("Refresh token no encontrado");
                    });

            if (!refreshToken.isValid()) {
                loggingService.logError("Refresh token {} inválido o expirado", refreshTokenValue);
                throw new InvalidTokenException("Refresh token inválido o expirado");
            }

            User user = repositoryUser.findByEmail(refreshToken.getUserEmail())
                    .orElseThrow(() -> {
                        loggingService.logError("Usuario no encontrado para refreshToken: {}", refreshTokenValue);
                        return new InvalidTokenException("Usuario no encontrado para el refresh token");
                    });

            loggingService.logDebug("Usuario encontrado para refreshToken: email={}", user.getEmail());

            if (!user.canLogin()) {
                loggingService.logError("Usuario con email {} no activo", user.getEmail());
                throw new UserNotActiveException(user.getEmail());
            }

            // Generar nuevos tokens
            String newAccessToken = generateAccessToken(user);
            String newRefreshTokenValue = generateRefreshToken();
            loggingService.logDebug("Nuevos tokens generados para email: {}", user.getEmail());

            // Revocar el refresh token actual
            refreshToken.revoke();
            repositoryAuthentication.saveRefreshToken(refreshToken);
            loggingService.logInfo("Refresh token actual revocado para email: {}", user.getEmail());

            // Revocar todos los demás tokens del usuario
            repositoryAuthentication.revokeAllTokensByUserEmail(user.getEmail());
            loggingService.logInfo("Todos los refresh tokens previos revocados para email: {}", user.getEmail());

            // Guardar nuevo refresh token
            saveRefreshToken(newRefreshTokenValue, user.getEmail());
            loggingService.logInfo("Nuevo refresh token guardado para email: {}", user.getEmail());

            // Calcular expiresIn basado en la configuración JWT
            Long expiresIn = 3600L;
            loggingService.logInfo("Token renovado exitosamente para email: {}", user.getEmail());

            return Login.succes(newAccessToken, newRefreshTokenValue, expiresIn, user);
        } catch (Exception e) {
            loggingService.logError("Error al renovar refreshToken {}: {}", refreshTokenValue, e.getMessage(), e);
            throw e;
        }
    }

    private void saveRefreshToken(String token, String userEmail) {
        loggingService.logDebug("Guardando refresh token para email: {}", userEmail);
        RefreshToken refreshToken = new RefreshToken(
            token, 
            userEmail, 
            LocalDateTime.now().plusDays(30), 
            false, 
            LocalDateTime.now()
        );
        repositoryAuthentication.saveRefreshToken(refreshToken);
        loggingService.logDebug("Refresh token guardado para email: {}", userEmail);
    }

    private void saveSuccessfulLogin(String email, String ipAddress, String userAgent) {
        loggingService.logDebug("Registrando intento de login exitoso para email: {} desde IP: {}", email, ipAddress);
        LoginAttempt attempt = LoginAttempt.successful(email, ipAddress, userAgent);
        repositoryAuthentication.saveLogin(attempt);
        loggingService.logDebug("Intento de login exitoso registrado para email: {}", email);
    }

    private void saveFailedLogin(String email, String ipAddress, String userAgent, String reason) {
        loggingService.logWarning("Registrando intento de login fallido para email: {}. Razón: {}", email, reason);
        LoginAttempt attempt = LoginAttempt.failed(email, ipAddress, userAgent, reason);
        repositoryAuthentication.saveLogin(attempt);
        loggingService.logDebug("Intento de login fallido registrado para email: {}", email);
    }

    // TODO: Implementar con JWT Service
    private String generateAccessToken(User user) {
        loggingService.logDebug("Generando access token para email: {}", user.getEmail());
        // Placeholder - implementar con tu JWT service
        return "placeholder_access_token_" + user.getEmail();
    }

    // TODO: Implementar con Token Service
    private String generateRefreshToken() {
        loggingService.logDebug("Generando refresh token");
        // Placeholder - implementar con tu token service
        return "placeholder_refresh_token_" + System.currentTimeMillis();
    }
}