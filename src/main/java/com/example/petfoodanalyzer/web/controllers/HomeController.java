package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.viewModels.products.BrandOverviewViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.ReviewOverviewViewModel;
import com.example.petfoodanalyzer.services.products.BrandService;
import com.example.petfoodanalyzer.services.products.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController extends BaseController{
    private final ReviewService reviewService;
    private final BrandService brandService;

    @Autowired
    public HomeController(ReviewService reviewService, BrandService brandService) {
        this.reviewService = reviewService;
        this.brandService = brandService;
    }

    @GetMapping("/")
    public ModelAndView guestHome(ModelAndView modelAndView){
        List<BrandOverviewViewModel> featuredBrands = this.brandService.findFeaturedBrands();
        modelAndView.addObject("featuredBrands", featuredBrands);

        List<ReviewOverviewViewModel> mostReviewed = this.reviewService.findMostReviewed();
        modelAndView.addObject("mostReviewed", mostReviewed);

        return super.view("index", modelAndView);
    }

    @GetMapping("/about")
    public ModelAndView getAbout(){
        return super.view("about");
    }
}
