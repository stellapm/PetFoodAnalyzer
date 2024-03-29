package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.repositories.users.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.petfoodanalyzer.constants.Exceptions.NAME_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Models.USER_ROLE;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

            @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public boolean isRoleTypesInit(){
                return this.userRoleRepository.count() > 0;
    }

    public void initUserRoleTypes() {
        if (isRoleTypesInit()){
            return;
        }

        List<UserRole> roleTypes = Arrays.stream(UserRoleTypes.values())
                .map(UserRole::new)
                .toList();

        this.userRoleRepository.saveAll(roleTypes);
    }

    public UserRole getUserRole(UserRoleTypes roleType) {
                return this.userRoleRepository
                        .findByRoleType(roleType)
                        .orElseThrow(() -> new ObjectNotFoundException(NAME_IDENTIFIER, roleType.name(), USER_ROLE));
    }

    private List<UserRole> getAllUserRoles(){
        return this.userRoleRepository.findAll();
    }

    public List<String> getAllUserRolesAsString() {
        return getAllUserRoles()
                .stream()
                .map(r -> r.getRoleType().name())
                .collect(Collectors.toList());
    }
}
