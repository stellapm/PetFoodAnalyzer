package com.example.petfoodanalyzer.models.entities.users;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.products.Product;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    private LocalDate created;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Pet> pets;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> favorites;

    public UserEntity() {
        this.userRoles = new HashSet<>();
        this.pets = new HashSet<>();
        this.favorites = new HashSet<>();
    }

    public UserEntity(String email, String password, String displayName, LocalDate created) {
        this();
        this.email = email;
        this.password = password;
        setDisplayName(displayName);
        this.created = created;
        setProfilePicUrl(null);
    }



    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserEntity setDisplayName(String displayName) {
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

    public UserEntity setProfilePicUrl(String profilePicUrl) {
        if (profilePicUrl == null || profilePicUrl.trim().isBlank()){
            profilePicUrl = "/images/profile.png";
        }

        this.profilePicUrl = profilePicUrl;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public UserEntity setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public UserEntity setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public UserEntity setPets(Set<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public Set<Product> getFavorites() {
        return favorites;
    }

    public UserEntity setFavorites(Set<Product> favorites) {
        this.favorites = favorites;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return email.equals(that.email) && displayName.equals(that.displayName) && profilePicUrl.equals(that.profilePicUrl) && created.equals(that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, displayName, profilePicUrl, created);
    }
}
