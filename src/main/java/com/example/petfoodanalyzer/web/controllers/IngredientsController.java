package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.ingredients.IngredientInfoDTO;
import com.example.petfoodanalyzer.models.dtos.ingredients.IngredientsListDTO;
import com.example.petfoodanalyzer.models.dtos.products.AddProductDTO;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
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
import java.util.Map;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController extends BaseController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientsController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @ModelAttribute(name = "ingredientsListDTO")
    public IngredientsListDTO ingredientsListDTO() {
        return new IngredientsListDTO();
    }

    @GetMapping("/analyze")
    public ModelAndView getAnalyze() {

        return super.view("analyze");
    }

    @PostMapping("/analyze")
    public ModelAndView postAddProduct(@Valid IngredientsListDTO ingredientsListDTO,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("ingredientsListDTO", ingredientsListDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ingredientsListDTO", bindingResult);

            return super.redirect("analyze");
        }

        Map<String, List<String>> ingredientBreakdown = this.ingredientService.analyzeIngredients(ingredientsListDTO);

        redirectAttributes.addFlashAttribute("ingredientBreakdown", ingredientBreakdown);

        return super.redirect("analyze");
    }

    @GetMapping("/all")
    public ModelAndView getAllIngredients(ModelAndView modelAndView) {
        List<IngredientInfoDTO> ingredients = this.ingredientService.getAllIngredients();
        modelAndView.addObject("ingredients", ingredients);

        return super.view("all-ingredients", modelAndView);
    }
}
