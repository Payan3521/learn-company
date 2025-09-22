package com.desarrollox.learncompany.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "administrators")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AdministratorEntity {
    
}
