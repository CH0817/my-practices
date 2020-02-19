package com.rex.practice.service;

public interface TokenService {

    String createRegisterToken(String userId);

    String getRegisterToken(String userId);

    boolean isTokenExpired(String userId) throws Exception;

    void deleteToken(String userId);

}
