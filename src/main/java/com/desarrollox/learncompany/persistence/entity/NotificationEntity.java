package com.desarrollox.learncompany.persistence.entity;

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
    
}