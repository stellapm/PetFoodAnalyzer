package com.example.petfoodanalyzer.models.entities.users;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.entities.products.Product;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email; //TODO: Email

    @Column(nullable = false)
    private String password; //TODO: At least 8 characters. REGEX for upper, lowercase, digits and special characters

    @Column(name = "display_name")
    private String displayName; //TODO: If no display name, assign username

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;

    @ManyToMany
    private Set<Pet> pets;

    @ManyToMany
    private Set<Product> favorites;

    public User() {
        this.userRoles = new HashSet<>();
        this.pets = new HashSet<>();
        this.favorites = new HashSet<>();
    }

    public User(String email, String password, String displayName) {
        this();
        this.email = email;
        this.password = password;
        setDisplayName(displayName);
        setProfilePicUrl(null);
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public User setDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isBlank()){
            this.displayName = this.email;
            return this;
        }

        this.displayName = displayName;
        return this;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public User setProfilePicUrl(String profilePicUrl) {
        if (profilePicUrl == null || profilePicUrl.trim().isBlank()){
            profilePicUrl = "/images/profile.png";
        }

        this.profilePicUrl = profilePicUrl;
        return this;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public User setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public User setPets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public Set<Product> getFavorites() {
        return favorites;
    }

    public User setFavorites(Set<Product> favorites) {
        this.favorites = favorites;
        return this;
    }
}
