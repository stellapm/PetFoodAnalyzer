package com.example.petfoodanalyzer.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController{
    //most reviewed
    //brands with most reviews

    @GetMapping("/")
    public ModelAndView guestHome(){
        return super.view("index");
    }

    @GetMapping("/about")
    public ModelAndView getAbout(){
        return super.view("about");
    }
}
