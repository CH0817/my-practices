package com.rex.practice.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

public class BindingResultUtils {

    public static String getFieldErrorMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted()
                .collect(Collectors.joining("，"));
    }

}
