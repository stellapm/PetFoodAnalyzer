package com.example.petfoodanalyzer.validators.annotations;


import com.example.petfoodanalyzer.validators.UniqueBrandNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueBrandNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueBrandName {
    String message() default "Brand with this name already already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}