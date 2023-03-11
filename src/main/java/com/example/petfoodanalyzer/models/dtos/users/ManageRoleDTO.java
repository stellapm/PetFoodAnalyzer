package com.example.petfoodanalyzer.models.dtos.users;

import com.example.petfoodanalyzer.validators.annotations.ExistingEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class ManageRoleDTO {
    @NotBlank(message = "Please enter email.")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Please enter a valid email.")
    @ExistingEmail
    private String email;

    @NotEmpty(message = "Please select a role")
    List<String> roles;

    public ManageRoleDTO() {
    }

    public String getEmail() {
        return email;
    }

    public ManageRoleDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public ManageRoleDTO setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}
