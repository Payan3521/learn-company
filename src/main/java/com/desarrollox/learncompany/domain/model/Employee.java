package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Employee extends User {
    private int puntos;
    private List<Certificate> certificates;
    private List<Inscription> inscriptions;

    public void addPoints(int points) {
        this.puntos += points;
    }
    
    public Employee() {
        super();
    }

    public Employee(Long id, String email, String password, String name, String lastname, boolean status,
                    Role role, Department department, String urlPhoto,
                    int puntos, List<Certificate> certificates, List<Inscription> inscriptions) {
        super(id, email, password, name, lastname, status, role, department, urlPhoto);
        this.puntos = puntos;
        this.certificates = certificates;
        this.inscriptions = inscriptions;
    }

    public Employee(String email, String password, String name, String lastname, boolean status, Role role,
            Department department, String urlPhoto, int puntos, List<Certificate> certificates,
            List<Inscription> inscriptions) {
        super(email, password, name, lastname, status, role, department, urlPhoto);
        this.puntos = puntos;
        this.certificates = certificates;
        this.inscriptions = inscriptions;
    }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public List<Certificate> getCertificates() { return certificates; }
    public void setCertificates(List<Certificate> certificates) { this.certificates = certificates; }

    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> inscriptions) { this.inscriptions = inscriptions; }
}