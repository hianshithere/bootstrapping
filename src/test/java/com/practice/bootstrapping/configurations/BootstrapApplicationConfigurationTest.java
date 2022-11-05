package com.practice.bootstrapping.configurations;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

//@TestPropertySource(value = "classpath:application.yml")
@SpringBootTest(classes = BootstrapApplicationConfiguration.class)
@Slf4j
class BootstrapApplicationConfigurationTest {

    @Autowired
    private BootstrapApplicationConfiguration configuration;

    @Test
    void getProperty() {
        log.info(configuration.getProperty());
    }
}