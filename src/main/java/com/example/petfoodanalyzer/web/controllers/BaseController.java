package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.web.interceptor.UserInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {
    public ModelAndView view(String viewName, ModelAndView modelAndView){
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    public ModelAndView view(String viewName){
        return this.view(viewName, new ModelAndView());
    }

    public ModelAndView redirect(String url){
        return this.view("redirect:" + url);
    }

    public static UserDetails getCurrentUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
