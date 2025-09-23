package com.desarrollox.learncompany.domain.model;

public class User {

    protected Long id;
    protected String email;
    protected String password;
    protected String name;
    protected String lastname;
    protected boolean status;
    protected Role role;
    protected Department department;
    protected String urlPhoto;

    public User() {
    }

    public User(Long id, String email, String password, String name, String lastname, boolean status, Role role, Department department, String urlPhoto) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.status = status;
        this.role = role;
        this.department = department;
        this.urlPhoto = urlPhoto;
    }

    public User(String email, String password, String name, String lastname, boolean status, Role role,
            Department department, String urlPhoto) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.status = status;
        this.role = role;
        this.department = department;
        this.urlPhoto = urlPhoto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getUrlPhoto() { return urlPhoto; }
    public void setUrlPhoto(String urlPhoto) { this.urlPhoto = urlPhoto; }

    public enum Role {
        EMPLOYEE,
        ADMINISTRATOR,
        INSTRUCTOR
    }
}
