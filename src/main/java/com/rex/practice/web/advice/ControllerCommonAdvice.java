package com.rex.practice.web.advice;

import com.rex.practice.exception.LostNecessaryParameterException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.rex.practice.web.controller")
public class ControllerCommonAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(LostNecessaryParameterException.class)
    public String lostNecessaryParameterException(LostNecessaryParameterException e) {
        logger.error("error: {}", e.getMessage(), e);
        return "redirect:" + checkUrlStartWithSlash(e.getRedirectUrl());
    }

    private String checkUrlStartWithSlash(String url) {
        assert StringUtils.isNotBlank(url);
        return url.startsWith("/") ? url : "/" + url;
    }

}
