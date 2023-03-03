package com.example.petfoodanalyzer.models.viewModels.users;

import com.example.petfoodanalyzer.models.entities.products.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class LoggedUserViewModel {
    private String email;

    private String displayName;

    private String profilePicUrl;

    private LocalDate created;

    private List<String> petsList;

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

    public List<String> getPetsList() {
        return petsList;
    }

    public LoggedUserViewModel setPetsList(List<String> petsList) {
        this.petsList = petsList;
        return this;
    }

    public boolean hasPet(String pet){

        if (this.petsList.contains(pet)){
            System.out.println(pet);
            return true;
        }

        return false;
    }
}
