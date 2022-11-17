package com.practice.bootstrapping.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.bootstrapping.wrapper.BootstrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class UserResponseReadyAspectTest {

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    MethodSignature methodSignature;

    @BeforeEach
    void setup() throws Throwable {
        proceedingJoinPoint = mock(ProceedingJoinPoint.class);
        methodSignature = mock(MethodSignature.class);
        when(proceedingJoinPoint.proceed()).thenReturn(Arrays.asList("anshit", "kumar", "sharma"));
        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("internalMethod");
        when(methodSignature.getReturnType()).thenReturn(String.class);

    }

    @Test
    @DisplayName("UserResponseReadyAspect happy path")
    void before() throws Throwable {
        UserResponseReadyAspect userResponseReadyAspect = new UserResponseReadyAspect();
        Object before = userResponseReadyAspect.before(proceedingJoinPoint);
        Class bootstrappingResponse = BootstrapResponse.class;
        assertFalse(before.equals(bootstrappingResponse));

        BootstrapResponse response = (BootstrapResponse) before;


        String abc = response.getResult().toString();

        List<String> list = Arrays.asList(abc.substring(1, abc.length() - 1).split(", "));
        System.out.println(list);


    }
}
