package com.desarrollox.learncompany.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.RefreshTokenEntity;

@Repository
public interface JpaRepositoryRefreshToken extends JpaRepository<RefreshTokenEntity, Long>{
    Optional<RefreshTokenEntity> findByToken(String token);
}