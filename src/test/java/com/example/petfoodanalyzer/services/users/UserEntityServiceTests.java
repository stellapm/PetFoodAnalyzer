package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.events.RegisteredUserEvent;
import com.example.petfoodanalyzer.models.dtos.users.EditUserProfileDTO;
import com.example.petfoodanalyzer.models.dtos.users.ManageRoleDTO;
import com.example.petfoodanalyzer.models.dtos.users.RegisterUserDTO;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.models.viewModels.products.ProductOverviewViewModel;
import com.example.petfoodanalyzer.models.viewModels.users.LoggedUserViewModel;
import com.example.petfoodanalyzer.repositories.users.UserEntityRepository;
import com.example.petfoodanalyzer.services.products.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTests {
    private UserEntityService testService;

    private UserRole user;
    private UserRole mod;
    private UserRole admin;

    private UserEntity firstUser;
    private UserEntity secondUser;
    private Product firstProduct;

    @Mock
    private UserEntityRepository mockRepository;

    @Mock
    private UserRoleService mockUserRoleService;

    @Mock
    private PetService mockPetService;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private EmailService mockEmailService;

    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;

    @BeforeEach
    public void setup() {
        setupUserRoles();

        this.firstProduct = new Product().setName("Product1");

        this.firstUser = new UserEntity()
                .setEmail("testEmail")
                .setDisplayName("test")
                .setProfilePicUrl("URL")
                .setPassword("password1");

        this.firstUser
                .getUserRoles().add(this.user);
        this.firstUser
                .getFavorites().add(this.firstProduct);

        this.secondUser = new UserEntity()
                .setEmail("testEmail2")
                .setDisplayName("test2")
                .setProfilePicUrl("URL")
                .setPassword("password1");

        this.secondUser
                .getUserRoles().add(this.user);
        this.secondUser
                .getFavorites().add(this.firstProduct);

        this.testService = new UserEntityService(mockRepository, mockUserRoleService, mockPetService,
                new ModelMapper(), mockPasswordEncoder, mockEmailService, mockApplicationEventPublisher);
    }

    private void setupUserRoles() {
        this.user = new UserRole(UserRoleTypes.USER);
        this.mod = new UserRole(UserRoleTypes.MODERATOR);
        this.admin = new UserRole(UserRoleTypes.ADMIN);
    }

    @Test
    public void testIsUserEntitiesDBInit() {
        when(this.mockRepository.count()).thenReturn(0L);
        assertFalse(this.testService.isUsersInit(), "Repository should be empty!");

        when(this.mockRepository.count()).thenReturn(1L);
        assertTrue(this.testService.isUsersInit(), "Repository should be populated!");
    }

    @Test
    public void testMakeAdminAddsUserRole() {
        when(this.mockUserRoleService.getUserRole(UserRoleTypes.ADMIN)).thenReturn(this.admin);

        this.testService.makeUserAdmin(this.firstUser);
        assertTrue(this.firstUser.getUserRoles().contains(this.admin));
    }

    @Test
    public void testRegisterCreatesNewUser(){
        RegisterUserDTO dto = new RegisterUserDTO()
                .setEmail("testEmail")
                .setPassword("test123")
                .setDisplayName("display name")
                .setTypes(List.of("Dog"));

        this.testService.register(dto);

        verify(this.mockRepository).save(any());
    }

    @Test
    public void testOnRegisteredUserCallsEmailService() {
        this.testService.onRegisteredUser(new RegisteredUserEvent("test"));

        verify(this.mockEmailService).sendRegistrationEmail("test");
    }

    @Test
    public void testGetUserProfileInfoReturnsCorrectEntityValues() {
        Pet cat = new Pet(PetsTypes.Cat);
        Pet dog = new Pet(PetsTypes.Dog);

        this.firstUser.getPets().add(cat);
        this.firstUser.getPets().add(dog);

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));

        LoggedUserViewModel result = this.testService.getProfileInfo("testEmail");

        assertEquals(this.firstUser.getEmail(), result.getEmail());
        assertEquals(this.firstUser.getDisplayName(), result.getDisplayName());
        assertEquals(this.firstUser.getProfilePicUrl(), result.getProfilePicUrl());
        assertEquals(this.firstUser.getPets().size(), result.getPetsList().size());

        assertTrue(result.getPetsList().contains("Cat"));
        assertTrue(result.getPetsList().contains("Dog"));
    }

    @Test
    public void testUpdateUserRolesAddsUserAlways() {
        ManageRoleDTO dto = new ManageRoleDTO()
                .setEmail("testEmail");

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));
        when(this.mockUserRoleService.getUserRole(UserRoleTypes.USER)).thenReturn(this.user);

        this.testService.updateUserRoles(dto);

        assertTrue(this.firstUser.getUserRoles().contains(this.user));
    }

    @Test
    public void testUpdateUserRoleAssignsRole() {
        ManageRoleDTO dto = new ManageRoleDTO()
                .setEmail("testEmail");

        dto.getRoles().add("MODERATOR");

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));
        when(this.mockUserRoleService.getUserRole(UserRoleTypes.USER)).thenReturn(this.user);
        when(this.mockUserRoleService.getUserRole(UserRoleTypes.MODERATOR)).thenReturn(this.mod);

        this.testService.updateUserRoles(dto);

        assertTrue(this.firstUser.getUserRoles().contains(this.mod));
        assertTrue(this.firstUser.getUserRoles().contains(this.user));
    }

    @Test
    public void testUpdateUserRoleRemovesAssignedRole() {
        this.firstUser.getUserRoles().add(admin);
        this.firstUser.getUserRoles().add(mod);

        ManageRoleDTO dto = new ManageRoleDTO()
                .setEmail("testEmail")
                .setRoles(List.of("MODERATOR", "USER"));

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));
        when(this.mockUserRoleService.getUserRole(UserRoleTypes.USER)).thenReturn(this.user);
        when(this.mockUserRoleService.getUserRole(UserRoleTypes.MODERATOR)).thenReturn(this.mod);

        this.testService.updateUserRoles(dto);

        assertTrue(this.firstUser.getUserRoles().contains(this.mod));
        assertTrue(this.firstUser.getUserRoles().contains(this.user));
        assertFalse(this.firstUser.getUserRoles().contains(this.admin));
    }

    @Test
    public void testUpdateUserRoleSavesEntity() {
        ManageRoleDTO dto = new ManageRoleDTO()
                .setEmail("testEmail");

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));
        when(this.mockUserRoleService.getUserRole(UserRoleTypes.USER)).thenReturn(this.user);

        this.testService.updateUserRoles(dto);

        verify(this.mockRepository).save(any());
    }

    @Test
    public void testFavoriteProductAddedToUser() {
        Product product = new Product()
                .setName("product");

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));

        testService.favoriteProduct(product, "testEmail");

        assertTrue(this.firstUser.getFavorites().contains(product));

        verify(mockRepository).save(any());
    }

    @Test
    public void getFavoritesReturnsCorrectProductsAndProductCount() {
        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));

        List<ProductOverviewViewModel> result = this.testService.getFavorites("testEmail");

        assertEquals(1, result.size());
        assertEquals(this.firstProduct.getName(), result.get(0).getName());
    }

    @Test
    public void testUpdateUserAllFieldsPresent(){
        EditUserProfileDTO dto = new EditUserProfileDTO()
                .setProfilePicUrl("newURL")
                .setEmail("newEmail")
                .setPassword("newPass123")
                .setDisplayName("newDisplay")
                .setTypes(List.of("Dog"));

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));

        Pet dog = new Pet(PetsTypes.Dog);
        when(this.mockPetService.getAllMatchingPetTypes(List.of("Dog"))).thenReturn(Set.of(dog));

        this.testService.updateLoggedUser(dto, "testEmail");

        assertEquals(dto.getProfilePicUrl(), this.firstUser.getProfilePicUrl());
        assertEquals(dto.getEmail(), this.firstUser.getEmail());
        assertEquals(dto.getDisplayName(), this.firstUser.getDisplayName());
        assertEquals(1, this.firstUser.getPets().size());
        assertTrue(this.firstUser.getPets().contains(dog));
    }

    @Test
    public void testUpdateUserPartialFields(){
        EditUserProfileDTO dto = new EditUserProfileDTO()
                .setProfilePicUrl("")
                .setEmail("newEmail")
                .setPassword("")
                .setDisplayName("")
                .setTypes(List.of("Dog"));

        when(this.mockRepository.findByEmail("testEmail")).thenReturn(Optional.ofNullable(this.firstUser));

        Pet dog = new Pet(PetsTypes.Dog);
        when(this.mockPetService.getAllMatchingPetTypes(List.of("Dog"))).thenReturn(Set.of(dog));

        this.testService.updateLoggedUser(dto, "testEmail");

        assertEquals("URL", this.firstUser.getProfilePicUrl());
        assertEquals(dto.getEmail(), this.firstUser.getEmail());
        assertEquals("test", this.firstUser.getDisplayName());
        assertEquals(1, this.firstUser.getPets().size());
        assertTrue(this.firstUser.getPets().contains(dog));
    }

    @Test
    public void testEncryptsExistingUserPass(){
        when(this.mockRepository.findAll()).thenReturn(List.of(this.firstUser, this.secondUser));

        this.testService.encryptImportedUserPasswords();

        verify(this.mockPasswordEncoder, times(2)).encode(any());
        verify(this.mockRepository).saveAll(any());
    }
}
