package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ObjectNotFoundAdvice {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView onProductNotFound(ObjectNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("object-not-found");

        modelAndView.addObject("objectType", exception.getObjectType());

        return modelAndView;
    }

}
