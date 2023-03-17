package com.example.petfoodanalyzer.init;

import com.example.petfoodanalyzer.services.ingredients.IngredientCategoryService;
import com.example.petfoodanalyzer.services.products.PetService;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import com.example.petfoodanalyzer.services.users.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class dbInit implements CommandLineRunner {
    private final IngredientCategoryService ingredientCategoryService;
    private final UserRoleService userRoleService;
    private final PetService petService;
    private final UserEntityService userEntityService;

    @Autowired
    public dbInit(IngredientCategoryService ingredientCategoryService, UserRoleService userRoleService, PetService petService, UserEntityService userEntityService) {
        this.ingredientCategoryService = ingredientCategoryService;
        this.userRoleService = userRoleService;
        this.petService = petService;
        this.userEntityService = userEntityService;
    }

    public void initServices() throws IOException {
        this.ingredientCategoryService.initIngredientCategories();
        this.petService.initPetTypes();
        this.userRoleService.initUserRoleTypes();
    }

    @Override
    public void run(String... args) throws Exception {
//        initServices();
        this.userEntityService.decryptImportedUserPasswords();
    }
}
