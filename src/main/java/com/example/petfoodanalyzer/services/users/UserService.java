package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.models.dtos.users.LoginUserDTO;
import com.example.petfoodanalyzer.models.dtos.users.RegisterUserDTO;
import com.example.petfoodanalyzer.models.entities.users.Pet;
import com.example.petfoodanalyzer.models.entities.users.User;
import com.example.petfoodanalyzer.models.entities.users.UserRole;
import com.example.petfoodanalyzer.models.enums.UserRoleTypes;
import com.example.petfoodanalyzer.models.helpers.LoggedUser;
import com.example.petfoodanalyzer.repositories.users.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    private boolean isUsersInit(){
        return this.userRepository.count() > 0;
    }

    private void makeUserAdmin(User user){
        UserRole adminRole = this.userRoleService.getUserRole(UserRoleTypes.Admin);
        user.getUserRoles().add(adminRole);
    }

    private void makeUserModerator(User user){
        UserRole moderatorRole = this.userRoleService.getUserRole(UserRoleTypes.Moderator);
        user.getUserRoles().add(moderatorRole);
    }

    public void register(RegisterUserDTO registerUserDTO) {
        User user = new User(registerUserDTO.getEmail(),
                this.passwordEncoder.encode(registerUserDTO.getPassword()),
                registerUserDTO.getDisplayName());

        Set<Pet> pets = this.petService.getAllMatchingPetTypes(registerUserDTO.getTypes());
        user.getPets().addAll(pets);

        if(!isUsersInit()){
            makeUserAdmin(user);
        }

        UserRole userRole = this.userRoleService.getUserRole(UserRoleTypes.User);
        user.getUserRoles().add(userRole);

        this.userRepository.save(user);
    }

    public boolean login(LoginUserDTO loginUserDTO){
        User user = this.findByEmail(loginUserDTO.getEmail());

        if (user == null){
            return false;
        }

        if (!this.passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())){
            return false;
        }

        this.loggedUser.login(user);

        return true;
    }

    public void logOut() {
        this.loggedUser.clearValues();
    }
}
