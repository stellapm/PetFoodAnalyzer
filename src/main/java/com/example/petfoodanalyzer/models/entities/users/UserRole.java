package com.example.petfoodanalyzer.models.entities.users;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleTypes roleType;

    public UserRole() {
    }

    public UserRole(UserRoleTypes roleType) {
        this.roleType = roleType;
    }

    public UserRoleTypes getRoleType() {
        return roleType;
    }

    public UserRole setRoleType(UserRoleTypes name) {
        this.roleType = name;
        return this;
    }
}
