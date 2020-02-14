package com.rex.practice.service;

public interface TokenService {

    String createRegisterToken(String email);

    String getRegisterToken(String email);

    boolean isTokenExpired(String email);

}
