package com.desarrollox.learncompany.persistence.entity;

import java.util.List;

import com.desarrollox.learncompany.domain.model.User.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "lastname", nullable = false)
    protected String lastname;

    @Column(name = "status", nullable = false)
    protected boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    protected Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    protected DepartmentEntity department;

    @Column(name = "url_photo", nullable = false)
    protected String urlPhoto;

}