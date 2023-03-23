package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.repositories.users.UserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTest {
    private final static String EXISTING_EMAIL = "existing@abv.bg";
    private final static String NON_EXISTING_EMAIL = "non_existing@abv.bg";

    private ApplicationUserDetailsService testService;

    @Mock
    private UserEntityRepository mockRepository;

    @BeforeEach
    public void setup(){
        testService = new ApplicationUserDetailsService(mockRepository);
    }

    @Test
    public void testLoadUserByUsername(){
        UserRole testAdminRole = new UserRole().setRoleType(UserRoleTypes.ADMIN);
        UserRole testUserRole = new UserRole().setRoleType(UserRoleTypes.USER);

        UserEntity testUserEntity = new UserEntity().
                setEmail(EXISTING_EMAIL).
                setPassword("test").
                setUserRoles(Set.of(testAdminRole, testUserRole));

        when(mockRepository.findByEmail(EXISTING_EMAIL)).thenReturn(Optional.ofNullable(testUserEntity));

        UserDetails testUserDetails = testService.loadUserByUsername(EXISTING_EMAIL);

        assertNotNull(testUserDetails);
        assertEquals(testUserEntity.getEmail(), testUserDetails.getUsername());
        assertEquals(testUserEntity.getPassword(), testUserDetails.getPassword());
        assertEquals(testUserEntity.getUserRoles().size(), testUserDetails.getAuthorities().size());

        assertRole(testUserDetails.getAuthorities(), "ROLE_ADMIN");
        assertRole(testUserDetails.getAuthorities(), "ROLE_USER");
    }

    private void assertRole(Collection<? extends GrantedAuthority> authorities, String role) {
        authorities.
                stream().
                filter(a -> role.equals(a.getAuthority())).
                findAny().
                orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
    }

    @Test
    void testUserNotFound() {
        assertThrows(
                UsernameNotFoundException.class,
                () -> testService.loadUserByUsername(NON_EXISTING_EMAIL));
    }
}
