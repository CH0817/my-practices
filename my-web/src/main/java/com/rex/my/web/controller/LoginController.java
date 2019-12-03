package com.rex.my.web.controller;

import com.rex.my.dao.entity.primary.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public String login(RedirectAttributes redirectAttributes, User user) {
        logger.info("user: {}", user);
        if (!"yu.chenhang@gmail.com".equals(user.getEmail()) || !"11111111".equals(user.getPassword())) {
            redirectAttributes.addFlashAttribute("message", "帳號密碼錯誤");
            return "redirect:/";
        }
        return "main";
    }

}
