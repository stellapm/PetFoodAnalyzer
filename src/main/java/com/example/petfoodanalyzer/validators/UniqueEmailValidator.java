package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.validators.annotations.UniqueEmail;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserEntityService userEntityService;

    public UniqueEmailValidator(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
//        return this.userEntityService.findByEmail(value) == null;

        try {
            this.userEntityService.findByEmail(value);
            return false;
        } catch (ObjectNotFoundException e){
            return true;
        }
    }
}
