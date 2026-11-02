package com.example.projectsync.model;

public class User {

    private String name;
    private String email;
    private String password;
    private String role;
    private boolean active;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = true;   // default active
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
