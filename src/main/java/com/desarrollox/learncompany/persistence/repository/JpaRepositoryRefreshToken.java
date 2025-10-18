package com.desarrollox.learncompany.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.RefreshTokenEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaRepositoryRefreshToken extends JpaRepository<RefreshTokenEntity, Long> {
    
    Optional<RefreshTokenEntity> findByToken(String token);
    
    List<RefreshTokenEntity> findByUserEmailAndRevokedFalse(String userEmail);
    
    @Modifying
    @Query("UPDATE RefreshTokenEntity r SET r.revoked = true, r.revokedAt = :revokedAt WHERE r.userEmail = :userEmail AND r.revoked = false")
    int revokeAllByUserEmail(@Param("userEmail") String userEmail, @Param("revokedAt") LocalDateTime revokedAt);
    
    @Modifying
    @Query("UPDATE RefreshTokenEntity r SET r.revoked = true, r.revokedAt = :revokedAt WHERE r.userEmail = :userEmail AND r.token <> :currentToken AND r.revoked = false")
    int revokeAllExceptCurrent(@Param("userEmail") String userEmail, @Param("currentToken") String currentToken, @Param("revokedAt") LocalDateTime revokedAt);
}
