package com.newProject.first.Validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = emailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface uniqueEmail  {

    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
    String message() default "Invalid Email";

}
