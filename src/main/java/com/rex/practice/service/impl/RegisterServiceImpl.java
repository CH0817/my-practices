package com.rex.practice.service.impl;

import com.rex.practice.model.input.Register;
import com.rex.practice.model.verify.RegisterError;
import com.rex.practice.service.EmailService;
import com.rex.practice.service.RegisterService;
import com.rex.practice.service.TokenService;
import com.rex.practice.service.UserService;
import com.rex.practice.service.base.BaseServiceImpl;
import com.rex.practice.util.BindingResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RegisterServiceImpl extends BaseServiceImpl implements RegisterService {

    private UserService userService;
    private EmailService emailService;
    private TokenService tokenService;

    @Autowired
    public RegisterServiceImpl(UserService userService, EmailService emailService, TokenService tokenService) {
        this.userService = userService;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<RegisterError> verify(Register register, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return Optional.of(new RegisterError(BindingResultUtils.getFieldErrorMessages(bindingResult),
                    "redirect:/register"));
        }
        if (!register.getPassword().equals(register.getConfirmPassword())) {
            return Optional.of(new RegisterError("兩次密碼不相同", "redirect:/register"));
        }
        if (userService.isEmailExists(register.getEmail())) {
            if (userService.isEmailVerified(register.getEmail())) {
                return Optional.of(new RegisterError("Email已被註冊", "redirect:/register"));
            }
            else {
                return Optional.of(new RegisterError("Email驗證中", "redirect:/login"));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean register(Register register) {
        if (userService.addUser(register)) {
            userService.findByEmail(register.getEmail())
                    .ifPresent(user -> emailService.sendConfirmRegisterEmail(user.getId(),
                            user.getEmail(),
                            tokenService.createRegisterToken(user.getId())));
            return true;
        }
        return false;
    }

}
