package com.example.petfoodanalyzer.models.entities.users;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return roleType == userRole.roleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleType);
    }
}
