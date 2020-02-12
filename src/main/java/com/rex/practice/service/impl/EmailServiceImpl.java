package com.rex.practice.service.impl;

import com.rex.practice.service.EmailService;
import com.rex.practice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${app.url}")
    private String appUrl;
    @Value("${app.port}")
    private String appPort;
    private JavaMailSender javaMailSender;
    private TokenService tokenService;
    private SpringTemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, TokenService tokenService, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.tokenService = tokenService;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendConfirmRegisterEmail(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();

        Map<String, Object> variables = new HashMap<>();
        String token = tokenService.getRegisterToken(email);
        variables.put("link", "http://" + appUrl + ":" + appPort + contextPath + "/register/verify/" + token);

        context.setVariables(variables);
        String html = templateEngine.process("email/registerConfirm", context);

        helper.setTo(email);
        helper.setText(html, true);
        helper.setSubject("Email認證信，請勿回復");
        helper.setFrom("yu.chenhang@gmail.com");

        javaMailSender.send(message);
    }

}
