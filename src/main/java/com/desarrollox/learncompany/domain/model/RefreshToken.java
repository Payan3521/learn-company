package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class RefreshToken {
    private Long id;
    private String token;
    private String userEmail;
    private LocalDateTime expiryDate;
    private boolean revoked;
    private LocalDateTime createdAt;
    private LocalDateTime revokedAt;
    
    public RefreshToken() {
    }

    public RefreshToken(Long id, String token, String userEmail, LocalDateTime expiryDate, boolean revoked,
            LocalDateTime createdAt, LocalDateTime revokedAt) {
        this.id = id;
        this.token = token;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
    }

    public RefreshToken(String token, String userEmail, LocalDateTime expiryDate, boolean revoked,
            LocalDateTime createdAt) {
        this.token = token;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
        this.createdAt = createdAt;
    }

    public RefreshToken(String token, String userEmail, LocalDateTime expiryDate, boolean revoked,
            LocalDateTime createdAt, LocalDateTime revokedAt) {
        this.token = token;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }

    public void revoke() {
        this.revoked = true;
        this.revokedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isRevoked() {
        return revoked;
    }
    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }
    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }
    
}