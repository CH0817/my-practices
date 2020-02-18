package com.rex.practice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/helper")
public class HelperController {

    @GetMapping("/register/verify/resend/")
    public String toConfirmResendRegisterMailPage(HttpServletRequest request) {
        return "help/confirm";
    }

}
