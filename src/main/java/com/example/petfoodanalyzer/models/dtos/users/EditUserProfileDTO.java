package com.example.petfoodanalyzer.models.dtos.users;

import com.example.petfoodanalyzer.validators.annotations.BlankOrPattern;
import com.example.petfoodanalyzer.validators.annotations.UniqueEmail;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Filter;

import java.util.List;

public class EditUserProfileDTO {
    @BlankOrPattern(regexp = "[\\w\\W]{5,}", message = "Please enter a valid URL.")
    private String profilePicUrl;

    @UniqueEmail
    @BlankOrPattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
      message = "Please enter a valid email.")
    private String email;

    @BlankOrPattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!.]).{6,15})",
            message = "Password should be between 6 and 15 characters and it should contain " +
                    "at least one digit, " +
                    "one upper case letter, " +
                    "one lower case letter and " +
                    "one special symbol (“@#$%”).")
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
}
