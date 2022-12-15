package com.practice.bootstrapping.configurations.asynchronous;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BootstrapApplicationAsyncConfigTest {

    BootstrapApplicationAsyncConfig bootstrapApplicationAsyncConfig;

    @BeforeEach
    void setup() {
        bootstrapApplicationAsyncConfig = new BootstrapApplicationAsyncConfig();
    }

    @Test
    void getAsyncExecutor() {
        Executor asyncExecutor = bootstrapApplicationAsyncConfig.getAsyncExecutor();
        assertNotNull(asyncExecutor);
    }

    @Test
    void getAsyncUncaughtExceptionHandler() {
        AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler =
                bootstrapApplicationAsyncConfig.getAsyncUncaughtExceptionHandler();
        assertEquals(asyncUncaughtExceptionHandler.getClass(),
                CustomAsyncExceptionHandler.class);
    }

    @Test
    void whenAsyncExceptionThenCustomExceptionHandlerIsThrown() {

    }
}