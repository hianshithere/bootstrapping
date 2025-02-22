package com.practice.bootstrapping.aop;

import org.aspectj.lang.ProceedingJoinPoint;

interface ResponseTransformer {
    Object transform(Object returnValue, ProceedingJoinPoint joinPoint);
}
