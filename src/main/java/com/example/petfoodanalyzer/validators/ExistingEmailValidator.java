package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.services.users.UserEntityService;
import com.example.petfoodanalyzer.validators.annotations.ExistingEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistingEmailValidator implements ConstraintValidator<ExistingEmail, String> {
        private final UserEntityService userEntityService;

    public ExistingEmailValidator(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return this.userEntityService.findByEmail(value) != null;
        }
}
