package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RefreshTokenEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "token_refresh", nullable = false, unique = true, length = 500)
    private String token;
    
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    
    @Column(name = "expiry_date_refresh", nullable = false)
    private LocalDateTime expiryDate;
    
    @Column(name = "revoked", nullable = false)
    private boolean revoked;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (expiryDate == null) {
            expiryDate = LocalDateTime.now().plusDays(30);
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        if (revoked && revokedAt == null) {
            revokedAt = LocalDateTime.now();
        }
    }
}
