package com.newProject.first.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class emailValidator implements ConstraintValidator<uniqueEmail,String> {
    @Override
    public void initialize(uniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null)
            return false;
        return s.matches("^[a-zA-Z][a-zA-Z0-9._+%-]*@[a-zA-z0-9]{1,}\\.[a-zA-z]{2,}$");
    }
}
