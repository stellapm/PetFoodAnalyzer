package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.ProductDetailsDTO;
import com.example.petfoodanalyzer.models.dtos.products.ProductOverviewInfoDTO;
import com.example.petfoodanalyzer.models.dtos.products.RecommendedProductDTO;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.services.products.ProductService;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController extends BaseController {
    private ProductService productService;
    private UserEntityService userEntityService;

    @Autowired
    public ProductsController(ProductService productService, UserEntityService userEntityService) {
        this.productService = productService;
        this.userEntityService = userEntityService;
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
        ProductDetailsDTO productDetailsDTO = this.productService.findById(id);
        modelAndView.addObject("productDetails", productDetailsDTO);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;

        System.out.println(auth.getPrincipal());

        if (!auth.getPrincipal().equals("anonymousUser")){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            user = this.userEntityService.findByEmail(userDetails.getUsername());
        }

        List<RecommendedProductDTO> recommendedProducts = this.productService.getRecommendedProducts(user, id);
        modelAndView.addObject("recommendedProducts", recommendedProducts);

        return super.view("product-details", modelAndView);
    }
}
