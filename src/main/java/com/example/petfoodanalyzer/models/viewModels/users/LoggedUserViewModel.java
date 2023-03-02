package com.example.petfoodanalyzer.models.viewModels.users;

import com.example.petfoodanalyzer.models.entities.products.Pet;

import java.time.LocalDate;
import java.util.Set;

public class LoggedUserViewModel {
    private String email;

    private String displayName;

    private String profilePicUrl;

    private LocalDate created;

    private Set<Pet> pets;

    public LoggedUserViewModel() {
    }

    public String getEmail() {
        return email;
    }

    public LoggedUserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public LoggedUserViewModel setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public LoggedUserViewModel setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LoggedUserViewModel setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public LoggedUserViewModel setPets(Set<Pet> pets) {
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
