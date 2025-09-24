package com.desarrollox.learncompany.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "statistics")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@SuperBuilder
public class StatisticEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // agrega las columnas reales que necesites
    // @Column(name = "...")
    // private Tipo campo;
}