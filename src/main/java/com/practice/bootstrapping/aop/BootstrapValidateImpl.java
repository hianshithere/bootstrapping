package com.practice.bootstrapping.aop;

import com.google.gson.Gson;
import com.practice.bootstrapping.configurations.properties.BootstrapApplicationConfiguration;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BootstrapValidateImpl implements ConstraintValidator<BootstrapValidate, String> {

  private final BootstrapApplicationConfiguration configuration;
   private Gson gson;

  public BootstrapValidateImpl(BootstrapApplicationConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      log.info("In validator implementations its Null");
      log.warn(getGson().toJson(configuration));
      return false;
    }
    log.info("In validator implementations its Not Null");
    log.warn(gson.toJson(configuration));

    return true;
  }

  public Gson getGson() {
    if (Objects.nonNull(gson)) {
      return gson;
    }
    return new Gson();
  }
}
