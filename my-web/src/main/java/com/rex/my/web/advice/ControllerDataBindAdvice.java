package com.rex.my.web.advice;

import com.rex.my.web.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.rex.my.web.controller")
public class ControllerDataBindAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BindException.class)
    public String nullPointExHandler(BindException e, RedirectAttributes redirectAttributes) {
        String errorMessage = e.getFieldErrors().stream()
                .peek(error -> logger.info("error field: {}, message: {}", error.getField(), error.getDefaultMessage()))
                .map(FieldError::getDefaultMessage)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.joining("、"));
        logger.info("error message: {}", errorMessage);
        redirectAttributes.addFlashAttribute("message", errorMessage);
        return "redirect:/";
    }

}
