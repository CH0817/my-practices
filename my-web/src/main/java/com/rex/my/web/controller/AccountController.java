package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    @RequestMapping("/content")
    public String content() {
        return "page/content/account";
    }

}
