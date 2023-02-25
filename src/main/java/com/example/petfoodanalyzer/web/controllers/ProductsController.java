package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.ProductOverviewInfoDTO;
import com.example.petfoodanalyzer.services.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController extends BaseController {
    private ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
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
}
