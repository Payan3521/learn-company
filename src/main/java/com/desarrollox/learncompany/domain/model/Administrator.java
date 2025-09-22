package com.desarrollox.learncompany.domain.model;

public class Administrator extends User{
    private int age;

    public Administrator() {
        super();
    }

    public Administrator(Long id, String email, String password, String name, String lastname, boolean status, Role role, Department department, String urlPhoto, int age) {
        super(id, email, password, name, lastname, status, role, department, urlPhoto);
        this.age = age;
    }

    public Administrator(String email, String password, String name, String lastname, boolean status, Role role, Department department, String urlPhoto, int age) {
        super(email, password, name, lastname, status, role, department, urlPhoto);
        this.age = age;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}