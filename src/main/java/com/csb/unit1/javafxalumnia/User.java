package com.csb.unit1.javafxalumnia;

public class User {
    private String username;
    private String role; // admin, alumno, profesor

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
}

