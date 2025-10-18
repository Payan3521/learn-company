package com.desarrollox.learncompany.domain.accessDb;

import java.util.Optional;
import com.desarrollox.learncompany.domain.model.LoginAttempt;
import com.desarrollox.learncompany.domain.model.RefreshToken;

public interface IRepositoryAuthentication {
    void saveRefreshToken(RefreshToken refreshToken);
    void saveLogin(LoginAttempt loginAttempt);
    Optional<RefreshToken> findByToken(String refreshTokenValue);
}