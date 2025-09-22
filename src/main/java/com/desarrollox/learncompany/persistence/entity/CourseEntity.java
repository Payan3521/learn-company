package com.desarrollox.learncompany.persistence.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CourseEntity {

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private int level;

    @Column(name = "duration_in_hours")
    private int durationInHours;

    @Column(name = "season_id")
    private SeasonEntity season;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private InstructorEntity instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleEntity> modules;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscriptionEntity> inscriptions;
}
