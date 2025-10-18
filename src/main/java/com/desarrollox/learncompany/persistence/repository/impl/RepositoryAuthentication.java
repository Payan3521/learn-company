package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAuthentication;
import com.desarrollox.learncompany.domain.model.LoginAttempt;
import com.desarrollox.learncompany.domain.model.RefreshToken;
import com.desarrollox.learncompany.persistence.mapper.AuthenticationMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryLoginAttempt;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryRefreshToken;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryAuthentication implements IRepositoryAuthentication{

    private final JpaRepositoryLoginAttempt jpaRepositoryLoginAttempt;
    private final JpaRepositoryRefreshToken jpaRepositoryRefreshToken;
    private final AuthenticationMapper authenticationMapper;
    
    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        jpaRepositoryRefreshToken.save(authenticationMapper.toEntity(refreshToken));
    }

    @Override
    public void saveLogin(LoginAttempt loginAttempt) {
        jpaRepositoryLoginAttempt.save(authenticationMapper.toEntity(loginAttempt));
    }

    @Override
    public Optional<RefreshToken> findByToken(String refreshTokenValue) {
        return jpaRepositoryRefreshToken.findByToken(refreshTokenValue).map(authenticationMapper::toModel);
    }
    
}