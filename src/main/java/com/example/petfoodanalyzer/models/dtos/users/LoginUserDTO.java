package com.example.petfoodanalyzer.models.dtos.users;

public class LoginUserDTO {
    private String email;
    private String password;

    public LoginUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public LoginUserDTO setEmail(String email) {
        this.email = email;
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
