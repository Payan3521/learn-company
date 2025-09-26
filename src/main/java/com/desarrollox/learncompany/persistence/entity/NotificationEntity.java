package com.desarrollox.learncompany.persistence.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_content_id", referencedColumnName = "id")
    private NotificationContentEntity content;

    @Column(name = "date_issued", nullable = false)
    private LocalDateTime dateIssued;

    @Column(name = "read_status", nullable = false)
    private boolean readStatus;

    @PrePersist
    protected void onCreate() {
        this.dateIssued = LocalDateTime.now();
    }
} 