package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.events.RegisteredUserEvent;
import com.example.petfoodanalyzer.models.viewModels.products.ProductOverviewViewModel;
import com.example.petfoodanalyzer.models.dtos.users.*;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.models.viewModels.users.LoggedUserViewModel;
import com.example.petfoodanalyzer.repositories.users.UserEntityRepository;
import com.example.petfoodanalyzer.services.products.PetService;
import com.example.petfoodanalyzer.services.products.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final UserRoleService userRoleService;
    private final PetService petService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;
    private final EmailService emailService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserEntityService(UserEntityRepository userEntityRepository, UserRoleService userRoleService, PetService petService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, ProductService productService, EmailService emailService, ApplicationEventPublisher applicationEventPublisher) {
        this.userEntityRepository = userEntityRepository;
        this.userRoleService = userRoleService;
        this.petService = petService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.productService = productService;
        this.emailService = emailService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private boolean isUsersInit() {
        return this.userEntityRepository.count() > 0;
    }

    public UserEntity findByEmail(String email) {
        return this.userEntityRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found."));
    }

    private void makeUserAdmin(UserEntity user) {
        UserRole adminRole = this.userRoleService.getUserRole(UserRoleTypes.ADMIN);
        user.getUserRoles().add(adminRole);
    }

    public void register(RegisterUserDTO registerUserDTO) {
        UserEntity user = new UserEntity(registerUserDTO.getEmail(),
                this.passwordEncoder.encode(registerUserDTO.getPassword()),
                registerUserDTO.getDisplayName(),
                LocalDate.now());

        Set<Pet> pets = this.petService.getAllMatchingPetTypes(registerUserDTO.getTypes());
        user.getPets().addAll(pets);

        if (!isUsersInit()) {
            makeUserAdmin(user);
        }

        UserRole userRole = this.userRoleService.getUserRole(UserRoleTypes.USER);
        user.getUserRoles().add(userRole);

        this.userEntityRepository.save(user);

        RegisteredUserEvent registeredUserEvent = new RegisteredUserEvent(user.getEmail());
        applicationEventPublisher.publishEvent(registeredUserEvent);
    }

    @EventListener(RegisteredUserEvent.class)
    public void onRegisteredUser(RegisteredUserEvent event){
        this.emailService.sendRegistrationEmail(event.getEmail());
    }

    public LoggedUserViewModel getProfileInfo(String email) {
        UserEntity user = findByEmail(email);

        LoggedUserViewModel loggedUserViewModel = this.modelMapper.map(user, LoggedUserViewModel.class);

        List<String> assignedPets = user.getPets().stream()
                .map(p -> p.getPetsType().name())
                .toList();

        loggedUserViewModel.setPetsList(assignedPets);

        return loggedUserViewModel;
    }

    public void updateUserRoles(ManageRoleDTO manageRoleDTO) {
        UserEntity user = findByEmail(manageRoleDTO.getEmail());

        if (!manageRoleDTO.getRoles().contains("USER")){
            manageRoleDTO.getRoles().add("USER");
        }

        Set<UserRole> newRoles = manageRoleDTO.getRoles()
                .stream()
                .map(r -> this.userRoleService.getUserRole(UserRoleTypes.valueOf(r)))
                .collect(Collectors.toSet());

        user.setUserRoles(newRoles);
        this.userEntityRepository.save(user);
    }

    public void favoriteProduct(Long productId, String username) {
        UserEntity user = findByEmail(username);
        user.getFavorites().add(this.productService.getProductById(productId));
        this.userEntityRepository.save(user);
    }

    public List<ProductOverviewViewModel> getFavorites(String username) {
        UserEntity user = findByEmail(username);

        return user.getFavorites()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductOverviewViewModel.class))
                .toList();
    }

    public void updateLoggedUser(EditUserProfileDTO editUserProfileDTO, String email) {
        UserEntity user = findByEmail(email);

        if (!editUserProfileDTO.getProfilePicUrl().trim().isBlank()){
            user.setProfilePicUrl(editUserProfileDTO.getProfilePicUrl());
        }

        if (!editUserProfileDTO.getEmail().trim().isBlank()){
            user.setEmail(editUserProfileDTO.getEmail());
        }

        if (!editUserProfileDTO.getPassword().trim().isBlank()){
            user.setPassword(this.passwordEncoder.encode(editUserProfileDTO.getPassword()));
        }

        if (!editUserProfileDTO.getDisplayName().trim().isBlank()){
            user.setDisplayName(editUserProfileDTO.getDisplayName());
        }

        Set<Pet> pets = this.petService.getAllMatchingPetTypes(editUserProfileDTO.getTypes());
        user.getPets().clear();
        user.getPets().addAll(pets);

        this.userEntityRepository.save(user);
    }
}
