package com.desarrollox.learncompany.persistence.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    
    @Override
    @Transactional
    public void saveRefreshToken(RefreshToken refreshToken) {
        jpaRepositoryRefreshToken.save(authenticationMapper.toEntity(refreshToken));
    }

    @Override
    @Transactional
    public void saveLogin(LoginAttempt loginAttempt) {
        jpaRepositoryLoginAttempt.save(authenticationMapper.toEntity(loginAttempt));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String refreshTokenValue) {
        return jpaRepositoryRefreshToken.findByToken(refreshTokenValue)
                .map(authenticationMapper::toModel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefreshToken> findActiveTokensByUserEmail(String userEmail) {
        return jpaRepositoryRefreshToken.findByUserEmailAndRevokedFalse(userEmail)
                .stream()
                .map(authenticationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void revokeAllTokensByUserEmail(String userEmail) {
        jpaRepositoryRefreshToken.revokeAllByUserEmail(userEmail, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void revokeAllTokensExceptCurrent(String userEmail, String currentToken) {
        jpaRepositoryRefreshToken.revokeAllExceptCurrent(userEmail, currentToken, LocalDateTime.now());
    }
}
