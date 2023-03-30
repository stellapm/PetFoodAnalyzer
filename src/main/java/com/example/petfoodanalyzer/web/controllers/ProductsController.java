package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.*;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.viewModels.products.*;
import com.example.petfoodanalyzer.services.products.BrandService;
import com.example.petfoodanalyzer.services.products.PetService;
import com.example.petfoodanalyzer.services.products.ProductService;
import com.example.petfoodanalyzer.services.products.ReviewService;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import com.example.petfoodanalyzer.web.interceptor.UserInterceptor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController extends BaseController {
    private final ProductService productService;
    private final UserEntityService userEntityService;
    private final BrandService brandService;
    private final PetService petService;

    @Autowired
    public ProductsController(ProductService productService, UserEntityService userEntityService, BrandService brandService, PetService petService) {
        this.productService = productService;
        this.userEntityService = userEntityService;
        this.brandService = brandService;
        this.petService = petService;
    }

    @GetMapping("/all")
    public ModelAndView getAll(ModelAndView modelAndView) {
        List<ProductOverviewViewModel> allProducts = this.productService.getAllProducts();

        modelAndView.addObject("allProducts", allProducts);

        return super.view("all-products", modelAndView);
    }

    @GetMapping("/favorites")
    public ModelAndView getFavorites(ModelAndView modelAndView) {
        UserDetails userDetails = getCurrentUserDetails();

        List<ProductOverviewViewModel> favoriteProducts = this.userEntityService.getFavorites(userDetails.getUsername());

        modelAndView.addObject("favoriteProducts", favoriteProducts);

        return super.view("favorite-products", modelAndView);
    }

    @GetMapping("/by-brand/{id}")
    public ModelAndView getProductsByBrand(@PathVariable Long id, ModelAndView modelAndView) {
        BrandViewModel brandInfo = this.brandService.getBrandInfoById(id);
        modelAndView.addObject("brandInfo", brandInfo);

        List<ProductOverviewViewModel> brandProducts = this.productService.getAllProductsByBrand(id);
        modelAndView.addObject("brandProducts", brandProducts);

        return super.view("products-by-brand", modelAndView);
    }

    @GetMapping("/details/{id}")
    public ModelAndView getProductDetails(@PathVariable Long id,
                                          @ModelAttribute("addReviewDTO") AddReviewDTO addReviewDTO,
                                          ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = null;

        if (UserInterceptor.isUserLogged()) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            user = this.userEntityService.findByEmail(userDetails.getUsername());
        }

        ProductViewModel productViewModel = this.productService.getProductDetailsById(user, id);
        modelAndView.addObject("productDetails", productViewModel);

        List<RecommendedProductViewModel> recommendedProducts = this.productService.getRecommendedProducts(user, id);
        modelAndView.addObject("recommendedProducts", recommendedProducts);

        return super.view("product-details", modelAndView);
    }

    @GetMapping("/fave-product/{id}")
    public ModelAndView faveProduct(@PathVariable Long id) {
        UserDetails user = getCurrentUserDetails();

        Product product = this.productService.getProductById(id);
        this.userEntityService.favoriteProduct(product, user.getUsername());

        return super.redirect("/products/details/" + id);
    }

    @ModelAttribute(name = "editProductDTO")
    public EditProductDTO editProductDTO(){
        return new EditProductDTO();
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView getEditProduct(@PathVariable Long id, ModelAndView modelAndView) {
        modelAndView.addObject("productId", id);

        List<String> allBrands = this.brandService.getAllBrandsNamesAsString();
        modelAndView.addObject("allBrands", allBrands);

        List<String> allPets = this.petService.getAllPetTypesAsString();
        modelAndView.addObject("allPets", allPets);

        EditProductViewModel editProductViewModel = this.productService.getEditedProductInfo(id);
        modelAndView.addObject("editProductViewModel", editProductViewModel);

        return super.view("edit-product", modelAndView);
    }

    @PostMapping("/edit-product/{id}")
    public ModelAndView postEditProduct(@PathVariable Long id,
                                        @Valid EditProductDTO editProductDTO,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editProductDTO", editProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editProductDTO", bindingResult);

            return super.redirect("/products/edit-product/" + id);
        }

        this.productService.editProduct(id, editProductDTO);

        return super.redirect("/products/details/" + id);
    }
}
