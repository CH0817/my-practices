package com.rex.practice.service.impl;

import com.rex.practice.model.input.Register;
import com.rex.practice.service.RegisterService;
import com.rex.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private JavaMailSender javaMailSender;

    @Autowired
    public RegisterServiceImpl(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
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
        if (userService.addUser(register)) {
            sendConfirmEmail(register.getEmail());
        }
    }

    private void sendConfirmEmail(String email) {
        // TODO 註冊後要發送檢核 email 信件，資料庫要增加確定欄位，確認時間，確認 URL 有效期限
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Email確認");
        msg.setText("點此連結前往重設密碼頁面\nhttps://www.google.com");
        javaMailSender.send(msg);
    }

    private String getFieldErrorDefaultMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("，"));
    }

}
