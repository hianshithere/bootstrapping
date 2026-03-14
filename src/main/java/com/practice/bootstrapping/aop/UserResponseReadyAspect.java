package com.practice.bootstrapping.aop;

import com.practice.bootstrapping.wrapper.BootstrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class UserResponseReadyAspect {
    @Around("@within(com.practice.bootstrapping.aop.UserResponseReady) || @annotation(com.practice.bootstrapping.aop.UserResponseReady)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Objects.requireNonNull(joinPoint, "joinPoint must not be null");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> returnType = methodSignature.getReturnType();

        Object returnValue = joinPoint.proceed();

        if (returnValue instanceof BootstrapResponse) {
            log.debug("Method {} returned a BootstrapResponse instance", methodSignature.getName());
            return returnValue;
        }

        if (BootstrapResponse.class.isAssignableFrom(returnType)) {
            log.debug("Declared return type of {} is BootstrapResponse", methodSignature.getName());
            return returnValue;
        }

        BootstrapResponse response = new BootstrapResponse();
        response.setResult(returnValue);
        response.setMetadata(methodSignature.getName());

        log.info("Wrapped return value of method {} into BootstrapResponse", methodSignature.getName());

        return response;
    }

    // Compatibility wrapper for callers/tests that expect a `before` method.
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        return aroundAdvice(joinPoint);
    }

}