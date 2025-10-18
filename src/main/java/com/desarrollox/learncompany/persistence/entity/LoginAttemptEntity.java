package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "login_attempts")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LoginAttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;
    
    @Column(name = "successful_attempt", nullable = false)
    private boolean successful;
    
    @Column(name = "failure_reason")
    private String failureReason;
    
    @Column(name = "attempt_time", nullable = false)
    private LocalDateTime attemptTime;
    
    @Column(name = "user_agent")
    private String userAgent;
}