package com.practice.bootstrapping.aop;

import com.practice.bootstrapping.wrapper.BootstrapResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserResponseReadyAspect {

    Log log = LogFactory.getLog (UserResponseReadyAspect.class);

    // annotations definition for Around
    @Around (value = "@within(com.practice.bootstrapping.aop.UserResponseReady) || "
            + "@annotation(com.practice.bootstrapping.aop.UserResponseReady)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        Object returnValue = joinPoint.proceed ();
        Object reponseOnMethodSignatureChange = null;

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature ();
        Class returnType = methodSignature.getReturnType ();
        Class bootstrappingResponse = BootstrapResponse.class;

        if (returnType.equals (bootstrappingResponse)) {
            System.out.println (bootstrappingResponse.getName ());
            reponseOnMethodSignatureChange = returnValue;
        } else {
            System.out.println ("!BootstrapResponse.class");

            BootstrapResponse response = new BootstrapResponse ();
            response.setResult (returnValue);
            response.setMetadata (methodSignature.getName ());
            reponseOnMethodSignatureChange = response;
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

        return reponseOnMethodSignatureChange;
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