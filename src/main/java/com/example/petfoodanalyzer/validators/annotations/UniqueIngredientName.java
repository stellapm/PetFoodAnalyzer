package com.example.petfoodanalyzer.validators.annotations;

import com.example.petfoodanalyzer.validators.UniqueIngredientNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueIngredientNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIngredientName {
    String message() default "Ingredient with this name already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}