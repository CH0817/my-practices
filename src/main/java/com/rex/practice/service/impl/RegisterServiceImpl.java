package com.rex.practice.service.impl;

import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.model.message.ErrorMessage;
import com.rex.practice.model.message.base.Message;
import com.rex.practice.model.verify.RegisterVerifyError;
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
    public Optional<Message> verify(Register register, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return Optional.of(new ErrorMessage(BindingResultUtils.getFieldErrorMessages(bindingResult),
                    "/register"));
        }
        if (!register.getPassword().equals(register.getConfirmPassword())) {
            return Optional.of(new ErrorMessage("兩次密碼不相同", "/register"));
        }
        if (userService.isEmailExists(register.getEmail())) {
            if (userService.isEmailVerified(register.getEmail())) {
                return Optional.of(new ErrorMessage("Email已被註冊", "/register"));
            }
            else {
                return Optional.of(new ErrorMessage("Email驗證中", "/login"));
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

    @Override
    public Optional<RegisterVerifyError> accountVerify(String userId, String token) throws Exception {
        RegisterVerifyError error = new RegisterVerifyError();
        if (!userService.findById(userId).isPresent()) {
            error.setAccountError(true);
            return Optional.of(error);
        }
        if (tokenService.isTokenExpired(userId) || !token.equals(tokenService.getRegisterToken(userId))) {
            error.setTokenError(true);
            return Optional.of(error);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateAccountToVerified(String userId) {
        return userService.updateEmailVerifyStatus(userId);
    }

    @Override
    public boolean resendVerifyEmail(String userId) {
        Optional<User> optionalUser = userService.findById(userId);
        boolean isUserExists = optionalUser.isPresent();
        if (isUserExists) {
            tokenService.deleteToken(userId);
            emailService.sendConfirmRegisterEmail(userId, optionalUser.get().getEmail(), tokenService.createRegisterToken(userId));
        }
        return isUserExists;
    }

}
