package com.example.petfoodanalyzer.models.dtos.users;

public class LoginUserDTO {
    private String username;
    private String password;

    public LoginUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public LoginUserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
