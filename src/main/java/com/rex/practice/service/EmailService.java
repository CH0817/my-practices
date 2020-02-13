package com.rex.practice.service;

public interface EmailService {

    void sendConfirmRegisterEmail(String email, String token);

}
