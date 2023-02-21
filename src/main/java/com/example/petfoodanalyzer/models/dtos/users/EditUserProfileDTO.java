package com.example.petfoodanalyzer.models.dtos.users;

import com.example.petfoodanalyzer.validators.annotations.UniqueEmail;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class EditUserProfileDTO {
    @Size(min = 5, message = "Please enter a valid URL.")
    private String profilePicUrl;

    @UniqueEmail
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Please enter a valid email.")
    private String email;

    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!.]).{6,15})",
            message = "Password should contain at least one digit, one upper case letter, one lower case letter and one special symbol (“@#$%”).")
    @Size(min = 6, max = 15, message = "Password should be between 6 and 15 characters.")
    private String password;

    private String confirmPassword;

    private String displayName;

    private List<String> types;

    public EditUserProfileDTO() {
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public EditUserProfileDTO setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EditUserProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public EditUserProfileDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public EditUserProfileDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public EditUserProfileDTO setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public List<String> getTypes() {
        return types;
    }

    public EditUserProfileDTO setTypes(List<String> types) {
        this.types = types;
        return this;
    }

    @Override
    public String toString() {
        return "EditUserProfileDTO{" +
                "profilePicUrl='" + profilePicUrl + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", displayName='" + displayName + '\'' +
                ", types=" + types.size() +
                '}';
    }
}
