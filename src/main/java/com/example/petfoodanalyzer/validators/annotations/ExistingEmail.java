package com.example.petfoodanalyzer.validators.annotations;


import com.example.petfoodanalyzer.validators.ExistingEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingEmail {
    String message() default "User with this email not found!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}