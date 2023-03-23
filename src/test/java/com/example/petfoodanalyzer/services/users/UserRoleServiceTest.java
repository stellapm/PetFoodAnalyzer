package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.repositories.users.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {
    private UserRoleService testService;

    private UserRole user;
    private UserRole moderator;
    private UserRole admin;

    @Mock
    private UserRoleRepository mockRepository;

    @BeforeEach
    public void setupUserRoles(){
        user = new UserRole()
                .setRoleType(UserRoleTypes.USER);
        moderator = new UserRole()
                .setRoleType(UserRoleTypes.MODERATOR);
        admin = new UserRole()
                .setRoleType(UserRoleTypes.ADMIN);

        this.testService = new UserRoleService(mockRepository);
    }

    //TODO: test init?

    @Test
    public void testGetUserRole(){
        when(mockRepository.findByRoleType(UserRoleTypes.USER))
                .thenReturn(Optional.ofNullable(this.user));

        UserRole result = this.testService.getUserRole(UserRoleTypes.USER);

        assertEquals(this.user, result);
    }

    @Test
    public void testGetUserRoleException(){
        when(mockRepository.findByRoleType(UserRoleTypes.USER))
                .thenReturn(Optional.empty());

        Throwable missingEnum = assertThrows(ObjectNotFoundException.class, () -> this.testService.getUserRole(UserRoleTypes.USER));
        assertEquals("Object with name USER and type User Role not found!", missingEnum.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testGetAllUserRolesAsString(){
        when(mockRepository.findAll()).thenReturn(List.of(user, moderator, admin));

        List<String> result = testService.getAllUserRolesAsString();

        assertEquals(3, result.size());
        assertEquals("USER", result.get(0));
        assertEquals("MODERATOR", result.get(1));
        assertEquals("ADMIN", result.get(2));
    }

}
