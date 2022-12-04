package com.practice.bootstrapping.aop;

import com.google.gson.Gson;
import com.practice.bootstrapping.configurations.BootstrapApplicationConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class BootstrapValidateImpl implements ConstraintValidator<BootstrapValidate, String> {

    @Autowired
    private BootstrapApplicationConfiguration configuration;

    final Gson gson = new Gson();

    public BootstrapValidateImpl() {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            log.info("In validator implementations its Null");
            log.warn(gson.toJson(configuration));
            return false;
        }
        log.info("In validator implementations its Not Null");
        log.warn(gson.toJson(configuration));

        return true;
    }
}
