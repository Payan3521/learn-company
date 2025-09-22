package com.desarrollox.learncompany.domain.model;

import java.util.List;

public class Employee extends User {
    private int puntos;
    private List<Badge> badges;
    private List<Certificate> certificates;
    private List<Inscription> inscriptions;
    
    public Employee() {
        super();
    }

    public Employee(Long id, String email, String password, String name, String lastname, boolean status,
                    Role role, Department department, String urlPhoto,
                    int puntos, List<Badge> badges, List<Certificate> certificates, List<Inscription> inscriptions) {
        super(id, email, password, name, lastname, status, role, department, urlPhoto);
        this.puntos = puntos;
        this.badges = badges;
        this.certificates = certificates;
        this.inscriptions = inscriptions;
    }

    public Employee(String email, String password, String name, String lastname, boolean status, Role role,
            Department department, String urlPhoto, int puntos, List<Badge> badges, List<Certificate> certificates,
            List<Inscription> inscriptions) {
        super(email, password, name, lastname, status, role, department, urlPhoto);
        this.puntos = puntos;
        this.badges = badges;
        this.certificates = certificates;
        this.inscriptions = inscriptions;
    }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public List<Badge> getBadges() { return badges; }
    public void setBadges(List<Badge> badges) { this.badges = badges; }

    public List<Certificate> getCertificates() { return certificates; }
    public void setCertificates(List<Certificate> certificates) { this.certificates = certificates; }

    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> inscriptions) { this.inscriptions = inscriptions; }
}
