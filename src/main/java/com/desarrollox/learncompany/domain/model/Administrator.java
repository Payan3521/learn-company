package com.desarrollox.learncompany.domain.model;

public class Administrator extends User{
    private int edad;

    public Administrator() {
        super();
    }

    public Administrator(Long id, String email, String password, String name, String lastname, boolean status, Role role, Department department, String urlPhoto, int edad) {
        super(id, email, password, name, lastname, status, role, department, urlPhoto);
        this.edad = edad;
    }

    public Administrator(String email, String password, String name, String lastname, boolean status, Role role, Department department, String urlPhoto, int edad) {
        super(email, password, name, lastname, status, role, department, urlPhoto);
        this.edad = edad;
    }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
}