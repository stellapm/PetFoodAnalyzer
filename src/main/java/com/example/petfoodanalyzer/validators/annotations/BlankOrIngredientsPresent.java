package com.example.petfoodanalyzer.validators.annotations;

import com.example.petfoodanalyzer.validators.BlankOrIngredientsPresentValidator;
import com.example.petfoodanalyzer.validators.IngredientsPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BlankOrIngredientsPresentValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BlankOrIngredientsPresent {
    String message() default "One or more ingredients not present.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}