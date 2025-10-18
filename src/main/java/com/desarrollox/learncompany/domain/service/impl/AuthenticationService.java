package com.desarrollox.learncompany.domain.service.impl;

import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public Login authenticate(String email, String password, String ipAddress, String userAgent) {

        if (!repositoryUser.existsByEmail(email)) {
            saveFailedLogin(email, ipAddress, userAgent, "Usuario no registrado");
            throw new UserNotFoundException("El usuario con email: " + email + " no existe");
        }

        User user = repositoryUser.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!user.canLogin()) {
            saveFailedLogin(email, ipAddress, userAgent, "Usuario no activo o no verificado");
            throw new UserNotActiveException(email);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            saveFailedLogin(email, ipAddress, userAgent, "Contraseña incorrecta");
            throw new InvalidCredentialsException(email);
        }

        // TODO: Generar access token con JWT
        String accessToken = generateAccessToken(user);
        
        // TODO: Generar refresh token
        String refreshTokenValue = generateRefreshToken();
        
        // Revocar todos los refresh tokens anteriores del usuario
        repositoryAuthentication.revokeAllTokensByUserEmail(user.getEmail());
        
        // Guardar nuevo refresh token
        saveRefreshToken(refreshTokenValue, user.getEmail());

        // Registrar intento exitoso
        saveSuccessfulLogin(email, ipAddress, userAgent);

        // TODO: Calcular expiresIn basado en la configuración JWT
        Long expiresIn = 3600L; // 1 hora por defecto

        return Login.succes(accessToken, refreshTokenValue, expiresIn, user);
    }

    @Override
    @Transactional
    public void logout(String refreshTokenValue) {
        RefreshToken refreshToken = repositoryAuthentication.findByToken(refreshTokenValue)
                .orElseThrow(() -> new InvalidTokenException("Refresh token no encontrado"));
        
        if (refreshToken.isRevoked()) {
            throw new InvalidTokenException("El refresh token ya fue revocado");
        }
        
        refreshToken.revoke();
        repositoryAuthentication.saveRefreshToken(refreshToken);
    }

    @Override
    @Transactional
    public Login refreshToken(String refreshTokenValue) {
        RefreshToken refreshToken = repositoryAuthentication.findByToken(refreshTokenValue)
                .orElseThrow(() -> new InvalidTokenException("Refresh token no encontrado"));
        
        if (!refreshToken.isValid()) {
            throw new InvalidTokenException("Refresh token inválido o expirado");
        }
        
        User user = repositoryUser.findByEmail(refreshToken.getUserEmail())
                .orElseThrow(() -> new InvalidTokenException("Usuario no encontrado para el refresh token"));
        
        if (!user.canLogin()) {
            throw new UserNotActiveException(user.getEmail());
        }

        // TODO: Generar nuevos tokens
        String newAccessToken = generateAccessToken(user);
        String newRefreshTokenValue = generateRefreshToken();

        // Revocar el refresh token actual
        refreshToken.revoke();
        repositoryAuthentication.saveRefreshToken(refreshToken);
        
        // Revocar todos los demás tokens del usuario excepto el actual (que ya fue revocado)
        repositoryAuthentication.revokeAllTokensByUserEmail(user.getEmail());

        // Guardar nuevo refresh token
        saveRefreshToken(newRefreshTokenValue, user.getEmail());

        // TODO: Calcular expiresIn basado en la configuración JWT
        Long expiresIn = 3600L;

        return Login.succes(newAccessToken, newRefreshTokenValue, expiresIn, user);
    }

    private void saveRefreshToken(String token, String userEmail) {
        RefreshToken refreshToken = new RefreshToken(
            token, 
            userEmail, 
            LocalDateTime.now().plusDays(30), 
            false, 
            LocalDateTime.now()
        );
        repositoryAuthentication.saveRefreshToken(refreshToken);
    }

    private void saveSuccessfulLogin(String email, String ipAddress, String userAgent) {
        LoginAttempt attempt = LoginAttempt.successful(email, ipAddress, userAgent);
        repositoryAuthentication.saveLogin(attempt);
    }

    private void saveFailedLogin(String email, String ipAddress, String userAgent, String reason) {
        LoginAttempt attempt = LoginAttempt.failed(email, ipAddress, userAgent, reason);
        repositoryAuthentication.saveLogin(attempt);
    }

    // TODO: Implementar con JWT Service
    private String generateAccessToken(User user) {
        // Placeholder - implementar con tu JWT service
        return "placeholder_access_token_" + user.getEmail();
    }

    // TODO: Implementar con Token Service
    private String generateRefreshToken() {
        // Placeholder - implementar con tu token service
        return "placeholder_refresh_token_" + System.currentTimeMillis();
    }
}
