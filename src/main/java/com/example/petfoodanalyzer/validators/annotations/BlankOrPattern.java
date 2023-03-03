package com.example.petfoodanalyzer.validators.annotations;

import com.example.petfoodanalyzer.validators.BlankOrPatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = BlankOrPatternValidator.class)
public @interface BlankOrPattern {
    String regexp();
    Pattern.Flag[] flags() default {};
    String message() default "{javax.validation.constraints.Pattern.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}