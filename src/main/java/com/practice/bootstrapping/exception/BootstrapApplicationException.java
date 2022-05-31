package com.practice.bootstrapping.exception;

import com.practice.bootstrapping.wrapper.BootstrapResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice()
public class BootstrapApplicationException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoDataFoundInBootstrapResponse.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public BootstrapResponse handleNoDataFoundException(@NotNull NoDataFoundInBootstrapResponse ex) {

		Map<String, Object> body = new TreeMap<>();
		body.put("localized-message", ex.getLocalizedMessage());
		body.put("message", ex.getMessage());

		return new BootstrapResponse(body, HttpStatus.NOT_FOUND);

	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new TreeMap<> ();
		body.put ("localized-message", ex.getLocalizedMessage ());
		body.put ("message", ex.getMessage ());

		return new ResponseEntity (body, status);
	}

}
