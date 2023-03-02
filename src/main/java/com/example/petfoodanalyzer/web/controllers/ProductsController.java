package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.*;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.views.products.EditProductViewModel;
import com.example.petfoodanalyzer.services.products.BrandService;
import com.example.petfoodanalyzer.services.products.PetService;
import com.example.petfoodanalyzer.services.products.ProductService;
import com.example.petfoodanalyzer.services.products.ReviewService;
import com.example.petfoodanalyzer.services.users.UserEntityService;
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
    private ProductService productService;
    private UserEntityService userEntityService;
    private ReviewService reviewService;
    private BrandService brandService;
    private PetService petService;

    @Autowired
    public ProductsController(ProductService productService, UserEntityService userEntityService, ReviewService reviewService, BrandService brandService, PetService petService) {
        this.productService = productService;
        this.userEntityService = userEntityService;
        this.reviewService = reviewService;
        this.brandService = brandService;
        this.petService = petService;
    }
    //product
    //compare
    //favorite products
    //products by brand

    @GetMapping("/all")
    public ModelAndView getAll(ModelAndView modelAndView) {
        List<ProductOverviewInfoDTO> allProducts = this.productService.getAllProducts();

        modelAndView.addObject("allProducts", allProducts);

        return super.view("all-products", modelAndView);
    }

    @GetMapping("/favorites")
    public ModelAndView getFavorites(ModelAndView modelAndView) {
        UserDetails userDetails = getCurrentUserDetails();

        List<ProductOverviewInfoDTO> favoriteProducts = this.userEntityService.getFavorites(userDetails.getUsername());

        modelAndView.addObject("favoriteProducts", favoriteProducts);

        return super.view("favorite-products", modelAndView);
    }

    @GetMapping("/by-brand/{id}")
    public ModelAndView getProductsByBrand(@PathVariable Long id, ModelAndView modelAndView) {
        BrandInfoDTO brandInfo = this.brandService.getBrandInfoById(id);
        modelAndView.addObject("brandInfo", brandInfo);

        List<ProductOverviewInfoDTO> brandProducts = this.productService.getAllProductsByBrand(id);
        modelAndView.addObject("brandProducts", brandProducts);

        return super.view("products-by-brand", modelAndView);
    }

    @GetMapping("/details/{id}")
    public ModelAndView getProductDetails(@PathVariable Long id, ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;

        if (!auth.getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            user = this.userEntityService.findByEmail(userDetails.getUsername());
        }

        ProductDetailsDTO productDetailsDTO = this.productService.getProductDetailsById(user, id);
        modelAndView.addObject("productDetails", productDetailsDTO);

        List<RecommendedProductDTO> recommendedProducts = this.productService.getRecommendedProducts(user, id);
        modelAndView.addObject("recommendedProducts", recommendedProducts);

        return super.view("product-details", modelAndView);
    }

    @ModelAttribute(name = "addReviewDTO")
    public AddReviewDTO addReviewDTO() {
        return new AddReviewDTO();
    }

    @PostMapping("/post-review/{id}")
    public ModelAndView postProductReview(@PathVariable Long id,
                                          @Valid AddReviewDTO addReviewDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addReviewDTO", addReviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addReviewDTO", bindingResult);

            return super.redirect("/products/details/" + id);
        }

        UserDetails user = getCurrentUserDetails();
        this.reviewService.saveReview(id, addReviewDTO, user.getUsername());

        return super.redirect("/products/details/" + id);
    }

    @GetMapping("/{pid}/like-review/{rid}")
    public ModelAndView likeProductReview(@PathVariable Long rid, @PathVariable Long pid) {
        UserDetails user = getCurrentUserDetails();

        this.reviewService.likeProductReview(rid, user.getUsername());

        return super.redirect("/products/details/" + pid);
    }

    @GetMapping("/fave-product/{id}")
    public ModelAndView faveProduct(@PathVariable Long id) {
        UserDetails user = getCurrentUserDetails();

        this.userEntityService.favoriteProduct(id, user.getUsername());

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
        modelAndView.addObject("editProductView", editProductViewModel);

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

//        this.productService.addProduct(addProductDTO);
        System.out.println(true);

        return super.redirect("/products/details/" + id);
    }
}
