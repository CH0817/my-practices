package com.rex.practice.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendConfirmRegisterEmail(String email) throws MessagingException;

}
