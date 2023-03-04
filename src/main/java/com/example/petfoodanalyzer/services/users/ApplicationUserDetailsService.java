package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.repositories.users.UserEntityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    public ApplicationUserDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByEmail(username)
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found."));
    }

    private UserDetails mapToUserDetails(UserEntity userEntity){
        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                extractAuthorities(userEntity));
    }

    private List<GrantedAuthority> extractAuthorities(UserEntity userEntity) {
        return userEntity
                .getUserRoles()
                .stream()
                .map(this::mapRole)
                .toList();
    }

    private GrantedAuthority mapRole(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getRoleType().name());
    }
}
