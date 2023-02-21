package com.example.petfoodanalyzer.models.helpers;

import com.example.petfoodanalyzer.models.entities.users.User;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.services.users.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@SessionScope
public class LoggedUser {
    private Long id;
    private String email;
    private Set<UserRole> userRoles;

    public LoggedUser(UserService userService) {
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

    public void login(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userRoles = user.getUserRoles();
    }

    public boolean isLogged() {
        return this.id != null;
    }

    public boolean isModerator(){
        return findRole(UserRoleTypes.Moderator);
    }

    public boolean isAdmin(){
        return findRole(UserRoleTypes.Admin);
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
