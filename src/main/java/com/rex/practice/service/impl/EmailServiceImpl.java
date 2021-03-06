package com.rex.practice.service.impl;

import com.rex.practice.service.EmailService;
import com.rex.practice.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl extends BaseServiceImpl implements EmailService {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${app.url}")
    private String appUrl;
    @Value("${app.port}")
    private String appPort;
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendConfirmRegisterEmail(String userId, String email, String token) {
        // TODO 這是用 Thymeleaf 做 template ，改用 freemarker 試試？
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            setMailHelper(message, email, mailContent(userId, token));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("send register verify email error", e);
        }
    }

    private Context mailContent(String userId, String token) {
        Context context = new Context();

        Map<String, Object> variables = new HashMap<>();
        variables.put("link", "http://" + appUrl + ":" + appPort + contextPath + "/register/verify/" + userId + "/" + token);

        context.setVariables(variables);

        return context;
    }

    private void setMailHelper(MimeMessage message, String email, Context context) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setTo(email);
        helper.setText(templateEngine.process("email/registerConfirm", context), true);
        helper.setSubject("Email認證信，請勿回復");
        helper.setFrom("yu.chenhang@gmail.com");
    }

}
