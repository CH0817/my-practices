package com.rex.practice.service.impl;

import com.rex.practice.model.input.Register;
import com.rex.practice.service.RegisterService;
import com.rex.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.stream.Collectors;

@Deprecated
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private UserService userService;

    @Autowired
    public RegisterServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<String> verify(Register register, BindingResult bindingResult) {
        Optional<String> result = Optional.empty();
        if (bindingResult.hasErrors()) {
            result = Optional.of(getFieldErrorDefaultMessages(bindingResult));
        }
        if (!register.getPassword().equals(register.getConfirmPassword())) {
            result = Optional.of("二次密碼不相同");
        }
        if (userService.isEmailExists(register.getEmail())) {
            result = Optional.of("Email已被註冊");
        }
        return result;
    }

    @Override
    public void register(Register register) {
        userService.addUser(register);
    }

    private String getFieldErrorDefaultMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("，"));
    }

}
