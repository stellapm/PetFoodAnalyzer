package com.example.petfoodanalyzer.models.helpers;

import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
public class LoggedUser {
    private Long id;
    private String email;
    private Set<UserRole> userRoles;

    public LoggedUser(UserEntityService userEntityService) {
        this.userRoles = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public LoggedUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public LoggedUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public LoggedUser setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public void login(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userRoles = user.getUserRoles();
    }

    public boolean isLogged() {
        return this.id != null;
    }

    public boolean isModerator(){
        return findRole(UserRoleTypes.MODERATOR);
    }

    public boolean isAdmin(){
        return findRole(UserRoleTypes.ADMIN);
    }

    public void clearValues(){
        this.id = null;
        this.email = null;
    }

    public boolean findRole(UserRoleTypes type){
        for (UserRole userRole : userRoles) {
            if (userRole.getRoleType().equals(type)){
                return true;
            }
        }
        return false;
    }
}
