package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.BrandOverviewDTO;
import com.example.petfoodanalyzer.services.products.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController extends BaseController{
    //most reviewed

    private BrandService brandService;

    @Autowired
    public HomeController(BrandService brandService) {
        this.brandService = brandService;
    }
    //brands with most products

    @GetMapping("/")
    public ModelAndView guestHome(ModelAndView modelAndView){
        List<BrandOverviewDTO> featuredBrands = this.brandService.findFeaturedBrands();

        modelAndView.addObject("featuredBrands", featuredBrands);

        return super.view("index", modelAndView);
    }

    @GetMapping("/about")
    public ModelAndView getAbout(){
        return super.view("about");
    }
}
