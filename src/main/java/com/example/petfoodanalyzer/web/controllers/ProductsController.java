package com.example.petfoodanalyzer.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductsController extends BaseController {

    //product
    //compare
    //all products
    //favorite products
    //products by brand

    @GetMapping("/all")
    public ModelAndView getAnalyze(){


        return super.view("all-products");
    }
}
