package com.example.petfoodanalyzer.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class FoodsController extends BaseController {

    //product
    //compare
    //all products
    //favorite products
    //products by brand

    @GetMapping("/analyze")
    public ModelAndView getAnalyze(){


        return super.view("analyze");
    }
}
