package com.desarrollox.learncompany.persistence.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id")
public class InstructorEntity extends UserEntity {

    @Column(name = "specialty", nullable = false)
    private String specialty;

    @Column(name = "biography", nullable = false)
    private String biography;

    @Column(name = "courses")
    private List<CourseEntity> courses;
}