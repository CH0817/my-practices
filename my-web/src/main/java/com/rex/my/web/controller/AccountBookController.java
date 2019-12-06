package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account-book")
public class AccountBookController extends BaseController {

    @RequestMapping("/content")
    public String content(Model model) {
        // FIXME 未完成
        return "page/content/account_book";
    }

}
