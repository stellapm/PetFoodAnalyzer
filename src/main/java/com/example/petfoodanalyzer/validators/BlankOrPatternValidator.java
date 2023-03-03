package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.validators.annotations.BlankOrPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BlankOrPatternValidator implements ConstraintValidator<BlankOrPattern, String> {
    private Pattern pattern;

    public void initialize(BlankOrPattern parameters) {
        jakarta.validation.constraints.Pattern.Flag[] flags = parameters.flags();
        int intFlag = 0;
        for (jakarta.validation.constraints.Pattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        try {
            pattern = Pattern.compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid regular expression.", e);
        }
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}