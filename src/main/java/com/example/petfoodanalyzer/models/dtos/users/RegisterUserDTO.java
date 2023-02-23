package com.example.petfoodanalyzer.models.dtos.users;

import com.example.petfoodanalyzer.validators.annotations.UniqueEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RegisterUserDTO {
    @UniqueEmail
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Please enter a valid email.")
    @NotBlank(message = "Please enter email.")
    private String email;

    @NotBlank(message = "Please enter password")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!.]).{6,15})",
            message = """
                    Password should contain at least:
                    one digit, one upper case letter, one lower case letter,
                    one special symbol (“@#$%”).""")
    @Size(min = 6, max = 15, message = "Password should be between 6 and 15 characters.")
    private String password;

    @NotBlank(message = "Please enter password.")
    private String confirmPassword;

    private String displayName;

    private List<String> types;

    public RegisterUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public RegisterUserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterUserDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public RegisterUserDTO setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public List<String> getTypes() {
        return types;
    }

    public RegisterUserDTO setTypes(List<String> types) {
        this.types = types;
        return this;
    }
}
