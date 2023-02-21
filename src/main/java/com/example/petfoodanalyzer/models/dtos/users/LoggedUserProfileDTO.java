package com.example.petfoodanalyzer.models.dtos.users;

import com.example.petfoodanalyzer.models.entities.users.Pet;
import com.example.petfoodanalyzer.models.entities.users.UserRole;

import java.time.LocalDate;
import java.util.Set;

public class LoggedUserProfileDTO {
    private String email;

    private String displayName;

    private String profilePicUrl;

    private LocalDate created;

    private Set<Pet> pets;

    public LoggedUserProfileDTO() {
    }

    public String getEmail() {
        return email;
    }

    public LoggedUserProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public LoggedUserProfileDTO setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public LoggedUserProfileDTO setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LoggedUserProfileDTO setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public LoggedUserProfileDTO setPets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public boolean hasPet(String pet){
        for (Pet currentPet : pets) {
            if(currentPet.getPetsType().name().equals(pet)){
                return true;
            }
        }
        return false;
    }
}
