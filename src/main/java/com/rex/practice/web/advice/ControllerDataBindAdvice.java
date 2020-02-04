package com.rex.practice.web.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.rex.practice.web.controller")
public class ControllerDataBindAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<String> nullPointExHandler(BindException e) {
        logger.error("error: {}", e.getMessage(), e);
        String errorMessage = e.getFieldErrors().stream()
                .peek(error -> logger.info("collect error field: {}, message: {}", error.getField(), error.getDefaultMessage()))
                .map(FieldError::getDefaultMessage)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.joining("、"));
        return createResponse(errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<String> ConstraintViolationException(ConstraintViolationException e) {
        logger.error("error: {}", e.getMessage(), e);
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .peek(m -> logger.info("collect error message: {}", m))
                .collect(Collectors.joining("、"));
        return createResponse(errorMessage);
    }

    private ResponseEntity<String> createResponse(String errorMessage) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8")
                .body(errorMessage);
    }

}
