package com.example.petfoodanalyzer.web.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.petfoodanalyzer.constants.Exceptions.ERROR_403;

@Controller
public class AnalyzerErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/" + ERROR_403;
            }
        }
        return "error/error";
    }
}
