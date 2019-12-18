package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController extends BaseController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/login";
    }

    /**
     * 轉向 login.html，同時也是 security 的 loginPage()
     *
     * @return login.html
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main")
    public String main() {
        return "page/main";
    }

    @RequestMapping("/login-error")
    public String loginError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "帳號或密碼錯誤");
        return "redirect:/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}