package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.model.message.ErrorMessage;
import com.rex.practice.model.message.InfoMessage;
import com.rex.practice.model.message.base.Message;
import com.rex.practice.model.verify.RegisterVerifyError;
import com.rex.practice.service.RegisterService;
import com.rex.practice.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/create")
    public String register(@Validated Register register, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Message> optionalError = registerService.verify(register, bindingResult);
        if (optionalError.isPresent()) {
            request.setAttribute("message", optionalError.get());
        }
        else {
            request.setAttribute("message", new InfoMessage("註冊成功，請至信箱收取驗證信", "/login"));
            if (!registerService.register(register)) {
                request.setAttribute("message", new ErrorMessage("註冊失敗", "/register"));
            }
        }
        return "forward:/helper/show/info";
    }

    @GetMapping("/verify/{userId}/{token}")
    public String accountVerify(@PathVariable String userId, @PathVariable String token, HttpServletRequest request)
            throws Exception {
        Optional<RegisterVerifyError> optionalError = registerService.accountVerify(userId, token);
        if (optionalError.isPresent()) {
            if (optionalError.get().isAccountError()) {
                return "redirect:/register";
            }
            if (optionalError.get().isTokenError()) {
                request.setAttribute("userId", userId);
                return "forward:/helper/register/verify/resend";
            }
        }

        registerService.updateAccountToVerified(userId);
        return "redirect:/login";
    }

    @PostMapping("/verify/resend")
    @ResponseBody
    public ResponseEntity<Boolean> resendVerifyEmail(@RequestParam String userId) {
        boolean isResendSuccess = registerService.resendVerifyEmail(userId);
        HttpStatus httpStatus = isResendSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(isResendSuccess);
    }

}