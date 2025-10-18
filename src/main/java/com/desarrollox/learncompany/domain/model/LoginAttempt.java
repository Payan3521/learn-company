package com.desarrollox.learncompany.domain.model;

import java.time.LocalDateTime;

public class LoginAttempt {
    
    private Long id;
    private String email;
    private String ipAddress;
    private boolean successful;
    private String failureReason;
    private LocalDateTime attemptTime;
    private String userAgent;

    public LoginAttempt() {
    }

    public LoginAttempt(Long id, String email, String ipAddress, boolean successful, String failureReason,
            LocalDateTime attemptTime, String userAgent) {
        this.id = id;
        this.email = email;
        this.ipAddress = ipAddress;
        this.successful = successful;
        this.failureReason = failureReason;
        this.attemptTime = attemptTime;
        this.userAgent = userAgent;
    }

    public LoginAttempt(String email, String ipAddress, boolean successful, String failureReason,
            LocalDateTime attemptTime, String userAgent) {
        this.email = email;
        this.ipAddress = ipAddress;
        this.successful = successful;
        this.failureReason = failureReason;
        this.attemptTime = attemptTime;
        this.userAgent = userAgent;
    }

    public LoginAttempt(String email, String ipAddress, boolean successful, LocalDateTime attemptTime,
            String userAgent) {
        this.email = email;
        this.ipAddress = ipAddress;
        this.successful = successful;
        this.attemptTime = attemptTime;
        this.userAgent = userAgent;
    }

    public static LoginAttempt successful(String email, String ipAddress, String userAgent) {
        return new LoginAttempt(email, ipAddress, true,  LocalDateTime.now(), userAgent);
    }

    public static LoginAttempt failed(String email, String ipAddress, String userAgent, String reason) {
        return new LoginAttempt(email, ipAddress, false, reason, LocalDateTime.now(), userAgent);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public boolean isSuccessful() { return successful; }
    public void setSuccessful(boolean successful) { this.successful = successful; }

    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }

    public LocalDateTime getAttemptTime() { return attemptTime; }
    public void setAttemptTime(LocalDateTime attemptTime) { this.attemptTime = attemptTime; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent;}
    
}