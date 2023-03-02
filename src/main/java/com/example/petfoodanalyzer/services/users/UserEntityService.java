package com.example.petfoodanalyzer.services.users;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final ProductService productService;

    @Autowired
    public UserEntityService(UserEntityRepository userEntityRepository, UserRoleService userRoleService, PetService petService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, ProductService productService) {
        this.userEntityRepository = userEntityRepository;
        this.userRoleService = userRoleService;
        this.petService = petService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.productService = productService;
    }

    public UserEntity findByEmail(String email) {
        return this.userEntityRepository.findByEmail(email);
    }

    private boolean isUsersInit() {
        return this.userEntityRepository.count() > 0;
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
    }

    public boolean login(LoginUserDTO loginUserDTO) {
        UserEntity user = this.findByEmail(loginUserDTO.getUsername());

        if (user == null) {
            return false;
        }

        if (!this.passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            return false;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);

        return true;
    }
    public LoggedUserViewModel getProfileInfo(String email) {
        UserEntity user = findByEmail(email);

        return this.modelMapper.map(user, LoggedUserViewModel.class);
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
