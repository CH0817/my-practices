package com.rex.practice.web.controller;

import com.rex.practice.exception.LostNecessaryParameterException;
import com.rex.practice.model.help.ConfirmParameter;
import com.rex.practice.model.help.Form;
import com.rex.practice.model.message.base.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/helper")
public class HelperController {

    @GetMapping("/register/verify/resend")
    public String toConfirmResendRegisterMailPage(HttpServletRequest request, Model model) {
        String userId = (String) request.getAttribute("userId");

        if (StringUtils.isBlank(userId)) {
            return "redirect:/login";
        }

        Map<String, Object> confirmFormParams = new HashMap<>();
        confirmFormParams.put("userId", userId);

        Form confirmForm = Form.builder().action("/register/verify/resend")
                .method("post")
                .parameter(confirmFormParams)
                .build();

        model.addAttribute("confirmParameter", ConfirmParameter.builder()
                .title("重發Email認證信")
                .message("是否重發Email認證信？")
                .confirmForm(confirmForm)
                .denyForm(new Form("/login"))
                .build());
        return "help/confirm";
    }

    @RequestMapping("/show/info")
    public String toShowMessagePage(HttpServletRequest request, Model model) {
        Message message = (Message) request.getAttribute("message");
        if (Objects.isNull(message)) {
            throw new LostNecessaryParameterException("message should not be null", "/login");
        }
        model.addAttribute("message", message);
        return "help/message";
    }

}
