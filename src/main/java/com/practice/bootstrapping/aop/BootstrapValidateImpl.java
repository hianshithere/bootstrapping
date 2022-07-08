package com.practice.bootstrapping.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class BootstrapValidateImpl implements ConstraintValidator<BootstrapValidate, String> {

    Log log = LogFactory.getLog (BootstrapValidateImpl.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            log.info ("In validator implementations its Null");
            return false;
        }
        log.info ("In validator implementations its NotNull");
        return true;
    }
}
