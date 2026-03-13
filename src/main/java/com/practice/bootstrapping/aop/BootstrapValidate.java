package com.practice.bootstrapping.aop;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE, LOCAL_VARIABLE })
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint (validatedBy = BootstrapValidateImpl.class)
public @interface BootstrapValidate {

    String message() default "The value provided is null";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
