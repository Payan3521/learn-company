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

    @Query(value = "SELECT * FROM refresh_tokens WHERE token = :token LIMIT 1", nativeQuery = true)
    Optional<RefreshTokenEntity> findByToken(@Param("token") String token);

    @Query(value = "SELECT * FROM refresh_tokens WHERE user_email = :userEmail AND revoked = FALSE", nativeQuery = true)
    List<RefreshTokenEntity> findByUserEmailAndRevokedFalse(@Param("userEmail") String userEmail);

    @Modifying
    @Query(value = """
        UPDATE refresh_tokens
        SET revoked = TRUE,
            revoked_at = :revokedAt
        WHERE user_email = :userEmail
          AND revoked = FALSE
        """, nativeQuery = true)
    int revokeAllByUserEmail(
        @Param("userEmail") String userEmail,
        @Param("revokedAt") LocalDateTime revokedAt
    );

    @Modifying
    @Query(value = """
        UPDATE refresh_tokens
        SET revoked = TRUE,
            revoked_at = :revokedAt
        WHERE user_email = :userEmail
          AND token <> :currentToken
          AND revoked = FALSE
        """, nativeQuery = true)
    int revokeAllExceptCurrent(
        @Param("userEmail") String userEmail,
        @Param("currentToken") String currentToken,
        @Param("revokedAt") LocalDateTime revokedAt
    );
}
