package com.desarrollox.learncompany.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_badges")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class EmployeeBadgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", referencedColumnName = "id", nullable = false)
    private BadgeEntity badge;

    @Column(name = "date_earned", nullable = false)
    private LocalDateTime dateEarned;

    @PrePersist
    protected void onCreate() {
        this.dateEarned = LocalDateTime.now();
    }
}