package com.rex.practice.exception;

import lombok.Getter;

@Getter
public class LostNecessaryParameterException extends RuntimeException {

    private String redirectUrl;

    public LostNecessaryParameterException(String message, String redirectUrl) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

}
