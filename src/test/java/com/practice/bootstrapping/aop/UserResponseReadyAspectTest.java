package com.practice.bootstrapping.aop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practice.bootstrapping.wrapper.BootstrapResponse;

@ExtendWith(MockitoExtension.class)
class UserResponseReadyAspectTest {

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Test
    @DisplayName("should_wrap_return_into_BootstrapResponse_when_method_returns_non_bootstrap")
    void should_wrap_return_into_BootstrapResponse_when_method_returns_non_bootstrap() throws Throwable {
        // Arrange
        when(proceedingJoinPoint.proceed()).thenReturn(Arrays.asList("anshit", "kumar", "sharma"));
        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("internalMethod");
        when(methodSignature.getReturnType()).thenReturn(String.class);

        UserResponseReadyAspect aspect = new UserResponseReadyAspect();

        // Act
        Object result = aspect.aroundAdvice(proceedingJoinPoint);

        // Assert
        assertThat(result).isInstanceOf(BootstrapResponse.class);

        BootstrapResponse response = (BootstrapResponse) result;
        assertThat(response.getMetadata()).isEqualTo("internalMethod");

        Object payload = response.getResult();
        assertThat(payload).isInstanceOf(List.class);
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) payload;

        assertThat(list).containsExactly("anshit", "kumar", "sharma");
    }
}
