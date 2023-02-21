package com.example.petfoodanalyzer.validators.annotations;

import com.example.petfoodanalyzer.validators.UniqueProductNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueProductNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductName {
    String message() default "Product with this name already already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}