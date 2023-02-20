package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.validators.annotations.UniqueEmail;
import com.example.petfoodanalyzer.services.users.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userService.findByEmail(value) == null;
    }
}
