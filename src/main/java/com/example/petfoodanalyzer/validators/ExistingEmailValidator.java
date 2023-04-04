package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import com.example.petfoodanalyzer.validators.annotations.ExistingEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ExistingEmailValidator implements ConstraintValidator<ExistingEmail, String> {
        private final UserEntityService userEntityService;

    public ExistingEmailValidator(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
//            return this.userEntityService.findByEmail(value) != null;

        try {
            this.userEntityService.findByEmail(value);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }
}
