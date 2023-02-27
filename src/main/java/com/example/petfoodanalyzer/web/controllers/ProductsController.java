package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.AddReviewDTO;
import com.example.petfoodanalyzer.models.dtos.products.ProductDetailsDTO;
import com.example.petfoodanalyzer.models.dtos.products.ProductOverviewInfoDTO;
import com.example.petfoodanalyzer.models.dtos.products.RecommendedProductDTO;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
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

    @Autowired
    public ProductsController(ProductService productService, UserEntityService userEntityService, ReviewService reviewService) {
        this.productService = productService;
        this.userEntityService = userEntityService;
        this.reviewService = reviewService;
    }
    //product
    //compare
    //favorite products
    //products by brand

    @GetMapping("/all")
    public ModelAndView getAnalyze(ModelAndView modelAndView){
        List<ProductOverviewInfoDTO> allProducts = this.productService.getAllProducts();

        modelAndView.addObject("allProducts", allProducts);

        return super.view("all-products", modelAndView);
    }

    @GetMapping("/details/{id}")
    public ModelAndView getProductDetails(@PathVariable Long id, ModelAndView modelAndView){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;

        if (!auth.getPrincipal().equals("anonymousUser")){
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
    public AddReviewDTO addReviewDTO(){
        return new AddReviewDTO();
    }

    @PostMapping("/post-review/{id}")
    public ModelAndView postProductReview(@PathVariable Long id,
                                          @Valid AddReviewDTO addReviewDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addReviewDTO", addReviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addReviewDTO", bindingResult);

            return super.redirect("/products/details/" + id);
        }

        UserDetails user = getCurrentUserDetails();
        this.reviewService.saveReview(id, addReviewDTO, user.getUsername());

        return super.redirect("/products/details/" + id);
    }

    @GetMapping("/{pid}/like-review/{rid}")
    public ModelAndView likeProductReview(@PathVariable Long rid, @PathVariable Long pid){
        UserDetails user = getCurrentUserDetails();

        this.reviewService.likeProductReview(rid, user.getUsername());

        return super.redirect("/products/details/" + pid);
    }
}
