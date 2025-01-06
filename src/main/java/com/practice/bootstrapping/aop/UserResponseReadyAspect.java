package com.practice.bootstrapping.aop;

import com.practice.bootstrapping.wrapper.BootstrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserResponseReadyAspect {

  @Around(
      value =
          "@within(com.practice.bootstrapping.aop.UserResponseReady) || "
              + "@annotation(com.practice.bootstrapping.aop.UserResponseReady)")
  public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

    Object returnValue = joinPoint.proceed();
    Object responseOnMethodSignatureChange = null;

    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Class returnType = methodSignature.getReturnType();
    Class bootstrappingResponse = BootstrapResponse.class;

    if (bootstrappingResponse.equals(returnType)) {
      log.warn("Composing response...");
      responseOnMethodSignatureChange = returnValue;
    } else {
      log.warn("Modifying to compose response...");

      BootstrapResponse response = new BootstrapResponse();
      response.setResult(returnValue);

      log.info(String.valueOf(returnValue));

      response.setMetadata(methodSignature.getName());
      responseOnMethodSignatureChange = response;
      joinPoint.proceed();
    }
    return responseOnMethodSignatureChange;
  }
}
