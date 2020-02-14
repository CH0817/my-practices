package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.model.verify.RegisterError;
import com.rex.practice.service.RegisterService;
import com.rex.practice.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping(value = {"", "/"})
    public String toRegister() {
        return "register";
    }

    @PostMapping(value = {"", "/"})
    public String register(@Validated Register register, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Optional<RegisterError> verifyOptional = registerService.verify(register, bindingResult);
        if (verifyOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("message", verifyOptional.get().getErrorMessage());
            return verifyOptional.get().getViewName();
        }

        registerService.register(register);
        return "redirect:/login";
    }

}
