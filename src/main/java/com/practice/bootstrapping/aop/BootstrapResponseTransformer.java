package com.practice.bootstrapping.aop;

import com.practice.bootstrapping.wrapper.BootstrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
class BootstrapResponseTransformer implements ResponseTransformer {

    @Override
    public Object transform(Object returnValue, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> returnType = methodSignature.getReturnType();

        return Optional.of(returnType)
                .filter(type -> type.equals(BootstrapResponse.class))
                .map(type -> {
                    log.warn("A Bootstrap Response");
                    return returnValue;
                })
                .orElseGet(() -> createBootstrapResponse(returnValue, methodSignature));
    }

    private BootstrapResponse createBootstrapResponse(Object returnValue, MethodSignature methodSignature) {
        log.warn("Not a Bootstrap Response");
        BootstrapResponse response = new BootstrapResponse();
        response.setResult(returnValue);
        response.setMetadata(methodSignature.getName());
        log.info(String.valueOf(returnValue));
        return response;
    }
}