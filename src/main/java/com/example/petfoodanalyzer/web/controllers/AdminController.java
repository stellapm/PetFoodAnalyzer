package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.AddBrandDTO;
import com.example.petfoodanalyzer.models.dtos.ingredients.AddIngredientDTO;
import com.example.petfoodanalyzer.models.dtos.products.AddProductDTO;
import com.example.petfoodanalyzer.models.dtos.users.ManageRoleDTO;
import com.example.petfoodanalyzer.services.products.BrandService;
import com.example.petfoodanalyzer.services.ingredients.IngredientCategoryService;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
import com.example.petfoodanalyzer.services.products.ProductService;
import com.example.petfoodanalyzer.services.products.PetService;
import com.example.petfoodanalyzer.services.users.UserRoleService;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private final IngredientCategoryService ingredientCategoryService;
    private final IngredientService ingredientService;
    private final UserRoleService userRoleService;
    private final BrandService brandService;
    private final UserEntityService userEntityService;
    private final PetService petService;
    private final ProductService productService;

    @Autowired
    public AdminController(IngredientCategoryService ingredientCategoryService, IngredientService ingredientService, BrandService brandService, UserRoleService userRoleService, UserEntityService userEntityService, PetService petService, ProductService productService) {
        this.ingredientCategoryService = ingredientCategoryService;
        this.ingredientService = ingredientService;
        this.brandService = brandService;
        this.userRoleService = userRoleService;
        this.userEntityService = userEntityService;
        this.petService = petService;
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getAdmin(){
        return super.view("admin-panel");
    }

    @ModelAttribute(name = "addIngredientDTO")
    public AddIngredientDTO addIngredientDTO(){
        return new AddIngredientDTO();
    }

    @GetMapping("/add-ingredient")
    public ModelAndView getAddIngredient(ModelAndView modelAndView){

        List<String> ingredientCategories = this.ingredientCategoryService.getAllIngredientCategoriesNames();
        modelAndView.addObject("ingredientCategories", ingredientCategories);

        return super.view("add-ingredient", modelAndView);
    }

    @PostMapping("/add-ingredient")
    public ModelAndView postAddIngredient(@Valid AddIngredientDTO addIngredientDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addIngredientDTO", addIngredientDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addIngredientDTO", bindingResult);

            return super.redirect("add-ingredient");
        }

        this.ingredientService.addIngredient(addIngredientDTO);

        return super.redirect("/admin");
    }

    @ModelAttribute(name = "manageRoleDTO")
    public ManageRoleDTO manageRoleDTO(){
        return new ManageRoleDTO();
    }

    @GetMapping("/manage-roles")
    public ModelAndView getManageRoles(ModelAndView modelAndView){

        List<String> roleNames = this.userRoleService.getAllUserRolesAsString();
        modelAndView.addObject("roleNames", roleNames);

        return super.view("manage-roles", modelAndView);
    }

    @PostMapping("/manage-roles")
    public ModelAndView postManageRoles(@Valid ManageRoleDTO manageRoleDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("manageRoleDTO", manageRoleDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manageRoleDTO", bindingResult);

            return super.redirect("manage-roles");
        }

        this.userEntityService.updateUserRoles(manageRoleDTO);

        return super.redirect("/admin");
    }

    @ModelAttribute(name = "addBrandDTO")
    public AddBrandDTO addBrandDTO(){
        return new AddBrandDTO();
    }

    @GetMapping("/add-brand")
    public ModelAndView getAddBrand(){

        return super.view("add-brand");
    }

    @PostMapping("/add-brand")
    public ModelAndView postAddBrand(@Valid AddBrandDTO addBrandDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addBrandDTO", addBrandDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addBrandDTO", bindingResult);

            return super.redirect("add-brand");
        }

        this.brandService.addBrand(addBrandDTO);

        return super.redirect("/admin");
    }

    @ModelAttribute(name = "addProductDTO")
    public AddProductDTO addProductDTO(){
        return new AddProductDTO();
    }

    @GetMapping("/add-product")
    public ModelAndView getAddProduct(ModelAndView modelAndView){
        List<String> allBrands = this.brandService.getAllBrandsNamesAsString();
        modelAndView.addObject("allBrands", allBrands);

        List<String> allPets = this.petService.getAllPetTypesAsString();
        modelAndView.addObject("allPets", allPets);

        return super.view("add-product", modelAndView);
    }

    @PostMapping("/add-product/")
    public ModelAndView postAddProduct(@Valid AddProductDTO addProductDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addProductDTO", addProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductDTO", bindingResult);

            return super.redirect("add-product");
        }

        this.productService.addProduct(addProductDTO);

        return super.redirect("/admin");
    }
}
