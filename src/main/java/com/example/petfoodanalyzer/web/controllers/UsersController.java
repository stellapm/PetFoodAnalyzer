package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.users.EditUserProfileDTO;
import com.example.petfoodanalyzer.models.viewModels.users.LoggedUserViewModel;
import com.example.petfoodanalyzer.models.dtos.users.LoginUserDTO;
import com.example.petfoodanalyzer.models.dtos.users.RegisterUserDTO;
import com.example.petfoodanalyzer.services.products.PetService;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController extends BaseController{
    private final UserEntityService userEntityService;
    private final PetService petService;

    //profile
    //admin

    @Autowired
    public UsersController(UserEntityService userEntityService, PetService petService) {
        this.userEntityService = userEntityService;
        this.petService = petService;
    }

    @ModelAttribute(name = "registerUserDTO")
    public RegisterUserDTO registerUserDTO(){
        return new RegisterUserDTO();
    }

    @GetMapping("/register")
    public ModelAndView getRegister(ModelAndView modelAndView){
        listPets(modelAndView);

        return super.view("register", modelAndView);
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@Valid RegisterUserDTO registerUserDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())){
            bindingResult.addError(new FieldError(
                    "differentConfirmPassword",
                    "confirmPassword",
                    "Passwords must be the same."));
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("registerUserDTO", registerUserDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerUserDTO",
                    bindingResult);
            return super.redirect("register");
        }

        this.userEntityService.register(registerUserDTO);

        return super.redirect("login");
    }

    @ModelAttribute(name = "loginUserDTO")
    public LoginUserDTO loginUserDTO(){
        return new LoginUserDTO();
    }

    @GetMapping("/login")
    public ModelAndView getLogin(){
        return super.view("login");
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@Valid LoginUserDTO loginUserDTO,
                                  RedirectAttributes redirectAttributes){

        if (!this.userEntityService.login(loginUserDTO)){
            redirectAttributes.addFlashAttribute("loginUserDTO", loginUserDTO);
            redirectAttributes.addFlashAttribute("invalidCreds", true);
            return super.redirect("login");
        }

        return super.redirect("/");
    }

    @ModelAttribute(name = "editUserProfileDTO")
    public EditUserProfileDTO editUserProfileDTO(){
        return new EditUserProfileDTO();
    }

    @GetMapping("/my-profile")
    public ModelAndView getMyProfile(ModelAndView modelAndView){

        listPets(modelAndView);

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LoggedUserViewModel profileInfo = this.userEntityService.getProfileInfo(user.getUsername());
        modelAndView.addObject("profileInfo", profileInfo);

        return super.view("profile", modelAndView);
    }

    @PatchMapping("/my-profile")
    public ModelAndView postMyProfile(@Valid EditUserProfileDTO editUserProfileDTO){

        //TODO: update on profile. Must support partial updates and validation
//        this.userService.updateLoggedUser(editUserProfileDTO);

        return super.redirect("my-profile");
    }

    @GetMapping("/logout")
    public ModelAndView getLogout(HttpSession httpSession){
        httpSession.invalidate();
        return super.redirect("/");
    }

    private void listPets(ModelAndView modelAndView) {
        List<String> petTypes = this.petService.getAllPetTypesAsString();
        modelAndView.addObject("petTypes", petTypes);
    }
}
