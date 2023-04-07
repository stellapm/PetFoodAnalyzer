package com.example.petfoodanalyzer.web.controllers.rest;

import com.example.petfoodanalyzer.services.products.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/all-brands")
public class BrandsRestController {
    private final BrandService brandService;

    @Autowired
    public BrandsRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    public Object fetchData(){
        return this.brandService.getAllBrandsOverviewInfo();
    }
}