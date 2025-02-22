package com.practice.bootstrapping.aop;

import com.practice.bootstrapping.wrapper.BootstrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class UserResponseReadyAspectTest {
    @Mock
    ProceedingJoinPoint proceedingJoinPoint;
    @Mock
    MethodSignature methodSignature;
    @Mock
    ResponseTransformer responseTransformer;

    @BeforeEach
    void setup() throws Throwable {
        proceedingJoinPoint = mock(ProceedingJoinPoint.class);
        methodSignature = mock(MethodSignature.class);
        responseTransformer = mock(ResponseTransformer.class);

        when(proceedingJoinPoint.proceed()).thenReturn(Arrays.asList("anshit", "kumar", "sharma"));
        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("internalMethod");
        when(methodSignature.getReturnType()).thenReturn(String.class);

    }

    @Test
    @DisplayName("UserResponseReadyAspect happy path")
    void userResponseAspect_andSuccess_test() throws Throwable {
        // Create a mock instance of BootstrapResponse
        BootstrapResponse mockResponse = new BootstrapResponse();
        mockResponse.setResult(Arrays.asList("anshit", "kumar", "sharma"));
        mockResponse.setMetadata("internalTestMethod");

        // Create a mock instance of BootstrapResponseTransformer
        BootstrapResponseTransformer mockResponseTransformer = mock(BootstrapResponseTransformer.class);

        // Set up the mock behavior for the transform method
        when(mockResponseTransformer.transform(any(), any())).thenReturn(mockResponse);

        // Create an instance of UserResponseReadyAspect and inject the mock
        UserResponseReadyAspect userResponseReadyAspect = new UserResponseReadyAspect();
        userResponseReadyAspect.responseTransformer = mockResponseTransformer; // Inject the mock

        // Call the before method
        Object before = userResponseReadyAspect.before(proceedingJoinPoint);

        // Assertions
        assertTrue(before instanceof BootstrapResponse); // Check if the result is an instance of BootstrapResponse

        BootstrapResponse response = (BootstrapResponse) before;
        assertEquals(Arrays.asList("anshit", "kumar", "sharma"), response.getResult());
        assertEquals("internalTestMethod", response.getMetadata());
    }

}
