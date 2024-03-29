package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.repositories.users.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTests {
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

    @Test
    public void testInitUserRolesNoActionOnInitDB() {
        when(this.mockRepository.count()).thenReturn(1L);

        this.testService.initUserRoleTypes();

        verify(this.mockRepository, times(0)).saveAll(any());
    }

    @Test
    public void testInitUserRolesOnEmptyDB() {
        when(this.mockRepository.count()).thenReturn(0L);

        this.testService.initUserRoleTypes();

        verify(this.mockRepository).saveAll(any());
    }

    @Test
    public void testIsRoleTypesInit(){
        when(this.mockRepository.count()).thenReturn(0L);
        assertFalse(this.testService.isRoleTypesInit(), "Repository should be empty!");

        when(this.mockRepository.count()).thenReturn(1L);
        assertTrue(this.testService.isRoleTypesInit(), "Repository should be populated!");
    }

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
