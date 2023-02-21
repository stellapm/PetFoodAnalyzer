package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.services.users.UserService;
import com.example.petfoodanalyzer.validators.annotations.ExistingEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistingEmailValidator implements ConstraintValidator<ExistingEmail, String> {
        private final UserService userService;

    public ExistingEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return this.userService.findByEmail(value) != null;
        }
}
