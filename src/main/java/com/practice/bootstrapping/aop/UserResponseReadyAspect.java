package com.practice.bootstrapping.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserResponseReadyAspect {

    @Autowired
    ResponseTransformer responseTransformer;

    @Around(value =
            "@within(com.practice.bootstrapping.aop.UserResponseReady) || "
                    + "@annotation(com.practice.bootstrapping.aop.UserResponseReady)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();
        return responseTransformer.transform(returnValue, joinPoint);
    }
}
