package com.github.mangelt.jwt.lab.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.mangelt.jwt.lab.domain.ReponseBodyPayload;
import com.github.mangelt.jwt.lab.exception.AppException;

@ControllerAdvice
public class AppExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {AppException.class})
	ResponseEntity<Object> handleAppExceptions(AppException ex, WebRequest request) {
		return handleExceptionInternal(ex, ReponseBodyPayload
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.content(ex.getCause())
				.build(), 
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
}
