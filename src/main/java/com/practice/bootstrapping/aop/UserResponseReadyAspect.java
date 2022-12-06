package com.practice.bootstrapping.aop;

import com.practice.bootstrapping.wrapper.BootstrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserResponseReadyAspect {


    // annotations definition for Around
    @Around (value = "@within(com.practice.bootstrapping.aop.UserResponseReady) || "
            + "@annotation(com.practice.bootstrapping.aop.UserResponseReady)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        Object returnValue = joinPoint.proceed ();
        Object responseOnMethodSignatureChange = null;

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature ();
        Class returnType = methodSignature.getReturnType ();
        Class bootstrappingResponse = BootstrapResponse.class;

        if (returnType.equals (bootstrappingResponse)) {
            log.warn ("A Bootstrap Response");
            responseOnMethodSignatureChange = returnValue;
        } else {
            log.warn ("Not a Bootstrap Response");

            BootstrapResponse response = new BootstrapResponse ();
            response.setResult (returnValue);

            log.info (String.valueOf(returnValue));

            response.setMetadata (methodSignature.getName ());
            responseOnMethodSignatureChange = response;
            joinPoint.proceed ();
        }
        /*
                if (joinPoint.getSignature () instanceof BootstrapResponse) {
                    System.out.println ("Class::");
                }
                System.out.println ("***********************");
                System.out.println ("I am in annotated method::" + pjp.getSignature ());
                System.out.println ("***********************");
        */

        return responseOnMethodSignatureChange;
    }

    // After each method return

//    @Around("execution(* com.practice.bootstrapping.controllers.UserController.*(..))")
//    public Object afterReturning(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        Object returnValue = joinPoint.proceed ();
//        Object reponseOnMethodSignatureChange = null;
//
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature ();
//        Class returnType = methodSignature.getReturnType ();
//        Class bootstrappingResponse = BootstrapResponse.class;
////        BootstrapResponse response = null;
//
//        System.out.println (returnValue);
//
//        if (returnType.equals (bootstrappingResponse)) {
//            System.out.println ("BootstrapResponse.class");
////
////			response = (BootstrapResponse) returnValue;
////			response.setMetadata("AM HERE");
//            reponseOnMethodSignatureChange = returnValue;
//        } else {
//            System.out.println ("!BootstrapResponse.class");
//
//            BootstrapResponse response = new BootstrapResponse ();
//            response.setResult (returnValue);
//            response.setMetadata ("AM AOP");
//
//            reponseOnMethodSignatureChange = response;
//            joinPoint.proceed ();
//        }
//
//        String descriptionAboutAnshit = "Anshit is a good typer";
//
//        String name = "Anshit";
//        return reponseOnMethodSignatureChange;
//
//    }

}