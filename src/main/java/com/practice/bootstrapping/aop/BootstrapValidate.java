package com.practice.bootstrapping.aop;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE, LOCAL_VARIABLE })
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint (validatedBy = BootstrapValidateImpl.class)
public @interface BootstrapValidate {

    String message() default "The value provided is null";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
