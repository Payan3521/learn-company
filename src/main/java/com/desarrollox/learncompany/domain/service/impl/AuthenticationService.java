package com.desarrollox.learncompany.domain.service.impl;

import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

        if(!repositoryUser.existsByEmail(email)){
            saveFailedLogin(email, ipAddress, userAgent, "Usuario no registrado");
            throw new UserNotFoundException("El usuario con email: " + email + "no existe");
        }

        if(!repositoryUser.findByEmail(email).get().canLogin()){
            saveFailedLogin(email, ipAddress, userAgent, "Usuario no activo o no verificado");
            throw new UserNotActiveException(email);
        }

        if(!passwordEncoder.matches(password, repositoryUser.findByEmail(email).get().getPassword())){
            saveFailedLogin(email, ipAddress, userAgent, "Contraseña incorrecta");
            throw new InvalidCredentialsException(email);
        }

        //generar token
        //generar refreshToken

        //guardar refresh token
        saveRefreshToken(null, repositoryUser.findByEmail(email).get().getEmail());

        //registrar intento exitoso
        saveSuccessfulLogin(email, ipAddress, userAgent);

        return Login.succes(null, null, null, repositoryUser.findByEmail(email).get());

    }

    private void saveRefreshToken(String token, String userEmail){
        RefreshToken refreshToken = new RefreshToken(token, userEmail, LocalDateTime.now().plusDays(30), false, LocalDateTime.now());
        repositoryAuthentication.saveRefreshToken(refreshToken);
    }

    private void saveSuccessfulLogin(String email, String ipAddress, String userAgent){
        LoginAttempt attempt = LoginAttempt.successful(email, ipAddress, userAgent);
        repositoryAuthentication.saveLogin(attempt);
    }

    private void saveFailedLogin(String email, String ipAddress, String userAgent, String reason){
        LoginAttempt attempt = LoginAttempt.failed(email, ipAddress, userAgent, reason);
        repositoryAuthentication.saveLogin(attempt);
    }

    @Override
    public void logout(String refreshTokenValue) {
        RefreshToken refreshToken = repositoryAuthentication.findByToken(refreshTokenValue).orElseThrow(() -> new InvalidTokenException("Refresh token no encontrado"));
        refreshToken.revoke();
        repositoryAuthentication.saveRefreshToken(refreshToken);
    }

    @Override
    public Login refreshToken(String refreshTokenValue) {
        RefreshToken refreshToken = repositoryAuthentication.findByToken(refreshTokenValue).orElseThrow(() -> new InvalidTokenException("Refresh token no encontrado"));
        if(!refreshToken.isValid()){
            throw new InvalidTokenException("Refresh token invalido o expirado");
        }
        
        User user = repositoryUser.findByEmail(refreshToken.getUserEmail()).orElseThrow(() -> new InvalidTokenException("Usuario no encontrado para el refresh token"));
        
        if(!user.canLogin()){
            throw new UserNotActiveException(user.getEmail());
        }

        //generar nuevos tokens

        //revocar el refresh token anterior
        refreshToken.revoke();
        repositoryAuthentication.saveRefreshToken(refreshToken);

        //guardar nuevo refresh token
        saveRefreshToken(null, user.getEmail());

        return Login.succes(null, null, null, user);
    }
    
}
