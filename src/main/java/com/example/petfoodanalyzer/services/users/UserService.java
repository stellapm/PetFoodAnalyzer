package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.models.dtos.users.*;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.users.User;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.models.helpers.LoggedUser;
import com.example.petfoodanalyzer.repositories.users.UserRepository;
import com.example.petfoodanalyzer.services.products.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PetService petService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser loggedUser;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleService userRoleService, PetService petService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.petService = petService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    private boolean isUsersInit() {
        return this.userRepository.count() > 0;
    }

    private void makeUserAdmin(User user) {
        UserRole adminRole = this.userRoleService.getUserRole(UserRoleTypes.Admin);
        user.getUserRoles().add(adminRole);
    }

    private void makeUserModerator(User user) {
        UserRole moderatorRole = this.userRoleService.getUserRole(UserRoleTypes.Moderator);
        user.getUserRoles().add(moderatorRole);
    }

    public void register(RegisterUserDTO registerUserDTO) {
        User user = new User(registerUserDTO.getEmail(),
                this.passwordEncoder.encode(registerUserDTO.getPassword()),
                registerUserDTO.getDisplayName(),
                LocalDate.now());

        Set<Pet> pets = this.petService.getAllMatchingPetTypes(registerUserDTO.getTypes());
        user.getPets().addAll(pets);

        if (!isUsersInit()) {
            makeUserAdmin(user);
        }

        UserRole userRole = this.userRoleService.getUserRole(UserRoleTypes.User);
        user.getUserRoles().add(userRole);

        this.userRepository.save(user);
    }

    public boolean login(LoginUserDTO loginUserDTO) {
        User user = this.findByEmail(loginUserDTO.getEmail());

        if (user == null) {
            return false;
        }

        if (!this.passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            return false;
        }

        this.loggedUser.login(user);

        return true;
    }

    public void logOut() {
        this.loggedUser.clearValues();
    }

    public LoggedUserProfileDTO getProfileInfo(String email) {
        User user = this.userRepository.findByEmail(email);

        return this.modelMapper.map(user, LoggedUserProfileDTO.class);
    }

    public void updateUserRoles(ManageRoleDTO manageRoleDTO) {
        User user = getByEmail(manageRoleDTO);

        if (!manageRoleDTO.getRoles().contains("User")){
            manageRoleDTO.getRoles().add("User");
        }

        Set<UserRole> newRoles = manageRoleDTO.getRoles()
                .stream()
                .map(r -> this.userRoleService.getUserRole(UserRoleTypes.valueOf(r)))
                .collect(Collectors.toSet());

        user.setUserRoles(newRoles);
        this.userRepository.save(user);
    }

    private User getByEmail(ManageRoleDTO manageRoleDTO) {
        return this.userRepository.findByEmail(manageRoleDTO.getEmail());
    }

//    public void updateLoggedUser(EditUserProfileDTO editUserProfileDTO) {
//        User user = this.userRepository.findByEmail(this.loggedUser.getEmail());
//
//
//
//        Set<Pet> pets = this.petService.getAllMatchingPetTypes(editUserProfileDTO.getTypes());
//        user.getPets().clear();
//        user.getPets().addAll(pets);
//
//        System.out.println(user);
//    }
}
