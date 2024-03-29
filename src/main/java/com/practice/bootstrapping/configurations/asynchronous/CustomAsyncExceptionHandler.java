package com.practice.bootstrapping.configurations.asynchronous;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		System.out.println("Exception message - " + ex.getMessage());
		System.out.println("Method name - " + method.getName());
		for (final Object param : params) {
			System.out.println("Param - " + param);
		}
	}

}
