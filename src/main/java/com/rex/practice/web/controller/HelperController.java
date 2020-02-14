package com.rex.practice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/helper")
public class HelperController {

    @GetMapping("/register/verify/resend/{email}")
    public String toConfirmResendRegisterMailPage(@PathVariable String email) {
        return "help/confirm";
    }

}
