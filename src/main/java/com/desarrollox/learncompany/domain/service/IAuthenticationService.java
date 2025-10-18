package com.desarrollox.learncompany.domain.service;

import com.desarrollox.learncompany.domain.model.Login;

public interface IAuthenticationService {
    Login authenticate(String email, String password, String ipAddress, String userAgent);
    void logout(String refreshTokenValue);
    Login refreshToken(String refreshTokenValue);
}