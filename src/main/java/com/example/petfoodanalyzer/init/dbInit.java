package com.example.petfoodanalyzer.init;

import com.example.petfoodanalyzer.services.products.IngredientCategoryService;
import com.example.petfoodanalyzer.services.users.PetService;
import com.example.petfoodanalyzer.services.users.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class dbInit implements CommandLineRunner {
    private IngredientCategoryService ingredientCategoryService;
    private UserRoleService userRoleService;
    private PetService petService;

    @Autowired
    public dbInit(IngredientCategoryService ingredientCategoryService, UserRoleService userRoleService, PetService petService) {
        this.ingredientCategoryService = ingredientCategoryService;
        this.userRoleService = userRoleService;
        this.petService = petService;
    }

    public void initServices() throws IOException {
        this.ingredientCategoryService.initIngredientCategories();
        this.petService.initPetTypes();
        this.userRoleService.initUserRoleTypes();
    }

    @Override
    public void run(String... args) throws Exception {
        initServices();
    }
}
