package com.rex.practice.service;

public interface EmailService {

    void sendConfirmRegisterEmail(String userId, String email, String token);

}
