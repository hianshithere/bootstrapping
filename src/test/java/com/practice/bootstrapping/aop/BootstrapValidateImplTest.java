package com.practice.bootstrapping.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.google.gson.Gson;
import com.practice.bootstrapping.configurations.properties.ApplicationMetadata;
import com.practice.bootstrapping.configurations.properties.BootstrapApplicationConfiguration;

@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties(BootstrapApplicationConfiguration.class)
class BootstrapValidateImplTest {

    @Mock
    private ConstraintValidatorContext validatorContext;

    @Mock
    private Gson gson;

    @Mock
    private BootstrapApplicationConfiguration configuration;

    @InjectMocks
    private BootstrapValidateImpl bootstrapValidate;

    @BeforeEach
    void setup() {
        lenient().when(gson.toJson(any())).thenReturn("it is mocked");
        lenient().when(configuration.getProperty()).thenReturn("JSR");
    }

    @Test
    void should_return_true_when_value_is_present() {
        // Arrange
        String value = "some text";

        // Act
        boolean result = bootstrapValidate.isValid(value, validatorContext);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_when_value_is_null() {
        // Arrange
        String value = null;

        // Act
        boolean result = bootstrapValidate.isValid(value, validatorContext);

        // Assert
        assertThat(result).isFalse();
    }

}