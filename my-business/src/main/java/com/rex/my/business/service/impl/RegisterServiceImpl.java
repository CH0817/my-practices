package com.rex.my.business.service.impl;

import com.rex.my.business.service.RegisterService;
import com.rex.my.business.service.UserService;
import com.rex.my.model.input.Register;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.stream.Collectors;

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
        if (userService.isUserExists(register.getEmail())) {
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
