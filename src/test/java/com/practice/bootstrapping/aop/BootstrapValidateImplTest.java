package com.practice.bootstrapping.aop;

import com.google.gson.Gson;
import com.practice.bootstrapping.configurations.properties.ApplicationMetadata;
import com.practice.bootstrapping.configurations.properties.BootstrapApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties(BootstrapApplicationConfiguration.class)
class BootstrapValidateImplTest {

    @Mock
    ConstraintValidatorContext validatorContext;
    @Mock
    Gson gson;
    @Mock
    BootstrapApplicationConfiguration configuration;
    @InjectMocks
    BootstrapValidateImpl bootstrapValidate;


    @BeforeEach
    void setup() {
        initMocks(this);
        lenient().when(gson.toJson(any())).thenReturn("it is mocked");
        BootstrapApplicationConfiguration applicationConfiguration = new BootstrapApplicationConfiguration();
        applicationConfiguration.setProperty("JSR");
        applicationConfiguration.setAppMetadata(
                ApplicationMetadata
                        .builder()
                        .appName("bootstrap")
                        .version("1.1")
                        .build());

        lenient().when(configuration.getProperty()).thenReturn("JSR");
    }

    @Test
    void isValid() {
        assertTrue(bootstrapValidate.isValid("some text", validatorContext));
    }

    @Test
    void isInvalid() {
        assertFalse(bootstrapValidate.isValid(null, validatorContext));
    }

}