package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.users.LoginUserDTO;
import com.example.petfoodanalyzer.models.dtos.users.RegisterUserDTO;
import com.example.petfoodanalyzer.models.helpers.LoggedUser;
import com.example.petfoodanalyzer.services.users.PetService;
import com.example.petfoodanalyzer.services.users.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController extends BaseController{
    private final LoggedUser loggedUser;
    private final UserService userService;
    private final PetService petService;

    //profile
    //admin

    @Autowired
    public UsersController(LoggedUser loggedUser, UserService userService, PetService petService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.petService = petService;
    }

    @ModelAttribute(name = "registerUserDTO")
    public RegisterUserDTO registerUserDTO(){
        return new RegisterUserDTO();
    }

    @ModelAttribute(name = "loginUserDTO")
    public LoginUserDTO loginUserDTO(){
        return new LoginUserDTO();
    }

    @GetMapping("/register")
    public ModelAndView getRegister(ModelAndView modelAndView){
        if (this.loggedUser.isLogged()){
            return super.redirect("/");
        }

        List<String> petTypes = this.petService.getAllPetTypesAsString();
        modelAndView.addObject("petTypes", petTypes);

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

        this.userService.register(registerUserDTO);

        return super.redirect("login");
    }

    @GetMapping("/login")
    public ModelAndView getLogin(){
        if (this.loggedUser.isLogged()){
            return super.redirect("/");
        }

        return super.view("login");
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@Valid LoginUserDTO loginUserDTO,
                                  RedirectAttributes redirectAttributes){

        if (!this.userService.login(loginUserDTO)){
            redirectAttributes.addFlashAttribute("loginUserDTO", loginUserDTO);
            redirectAttributes.addFlashAttribute("invalidCreds", true);
            return super.redirect("login");
        }

        return super.redirect("/");
    }

    @GetMapping("/myProfile")
    public ModelAndView getMyProfile(){
        if (!loggedUser.isLogged()){
            return super.redirect("login");
        }

        return super.view("profile");
    }

    @GetMapping("/logout")
    public ModelAndView getLogout(){
        if (!this.loggedUser.isLogged()){
            return super.redirect("/");
        }

        this.userService.logOut();
        return super.redirect("/");
    }
}
