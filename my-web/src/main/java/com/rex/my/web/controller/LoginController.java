package com.rex.my.web.controller;

import com.rex.my.business.service.LoginService;
import com.rex.my.business.service.UserService;
import com.rex.my.model.vo.Login;
import com.rex.my.web.constant.SessionAttribute;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController extends BaseController {

    private LoginService loginService;
    private UserService userService;

    @Autowired
    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login(HttpSession session, RedirectAttributes redirectAttributes, @Validated Login login) {
        logger.info("login: {}", login);
        if (!loginService.login(login)) {
            redirectAttributes.addFlashAttribute("message", "帳號密碼錯誤");
            return "redirect:/";
        }
        session.setAttribute(SessionAttribute.USER, userService.findByEmail(login.getEmail()));
        return "page/main";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}