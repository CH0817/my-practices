package com.rex.my.web.controller;

import com.rex.my.business.service.LoginService;
import com.rex.my.model.vo.Login;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController extends BaseController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/login")
    public String login(RedirectAttributes redirectAttributes, @Validated Login login) {
        logger.info("login: {}", login);
        if (!loginService.login(login)) {
            redirectAttributes.addFlashAttribute("message", "帳號密碼錯誤");
            return "redirect:/";
        }
        return "page/main";
    }

}