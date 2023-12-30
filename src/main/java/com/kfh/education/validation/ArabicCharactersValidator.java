package com.kfh.education.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;

public class ArabicCharactersValidator implements ConstraintValidator<ArabicCharacters, String> {
    @Override
    public void initialize(ArabicCharacters constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Validate that the value contains only Arabic characters
        return value != null && value.matches("\\p{InArabic}+");
    }
}
