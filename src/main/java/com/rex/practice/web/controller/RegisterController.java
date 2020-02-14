package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.service.EmailService;
import com.rex.practice.service.TokenService;
import com.rex.practice.service.UserService;
import com.rex.practice.util.BindingResultUtils;
import com.rex.practice.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

    private UserService userService;
    private EmailService emailService;
    private TokenService tokenService;

    @Autowired
    public RegisterController(UserService userService, EmailService emailService, TokenService tokenService) {
        this.userService = userService;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @GetMapping(value = {"", "/"})
    public String toRegister() {
        return "register";
    }

    @PostMapping(value = {"", "/"})
    public String register(@Validated Register register, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            redirectAttributes.addFlashAttribute("message", BindingResultUtils.getFieldErrorMessages(bindingResult));
            return "redirect:/register";
        }
        if (!register.getPassword().equals(register.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("message", "兩次密碼不相同");
            return "redirect:/register";
        }
        if (userService.isEmailExists(register.getEmail())) {
            if (userService.isEmailVerified(register.getEmail())) {
                redirectAttributes.addFlashAttribute("message", "Email已被註冊");
                return "redirect:/register";
            }
            else {
                redirectAttributes.addFlashAttribute("message", "Email驗證中");
                return "redirect:/login";
            }
        }
        if (userService.addUser(register)) {
            emailService.sendConfirmRegisterEmail(register.getEmail(), tokenService.createRegisterToken(register.getEmail()));
        }
        return "redirect:/login";
    }

}
