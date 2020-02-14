package com.rex.practice.web.controller;

import com.rex.practice.model.recapthcha.ReCaptchaProperty;
import com.rex.practice.model.recapthcha.ReCaptchaResponse;
import com.rex.practice.service.UserService;
import com.rex.practice.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends BaseController {

    private ReCaptchaProperty reCaptchaProperty;
    private UserService userService;
    private JavaMailSender javaMailSender;

    @Autowired
    public LoginController(UserService userService, ReCaptchaProperty reCaptchaProperty, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.reCaptchaProperty = reCaptchaProperty;
        this.javaMailSender = javaMailSender;
    }

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
    public String login(HttpServletRequest request) {
        if (request.isUserInRole("USER")) {
            return "redirect:/main";
        }
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

    @GetMapping("/forget")
    public String toForgetPassword() {
        return "resetPassword_02";
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam(value = "g-recaptcha-response", required = false) String token,
                                RedirectAttributes redirectAttributes) {
        if (!userService.isEmailExists(email)) {
            redirectAttributes.addFlashAttribute("message", "Email不存在");
            return "redirect:/forget";
        }
        if (!getReCaptchaVerifyResponse(reCaptchaProperty.getV2Checkbox(), token).getSuccess()) {
            return "redirect:/forget";
        }
        sendResetPasswordEmail(email);
        return "redirect:/login";
    }

    @PostMapping("/v3")
    @ResponseBody
    public String forgetPasswordReCaptchaV3(String token) {
        // TODO reCAPTCHA V3
        System.out.println(token);
        System.out.println(getReCaptchaVerifyResponse(reCaptchaProperty.getV3(), token));
        return "XD";
    }

    @PostMapping("/v2i")
    public String forgetPasswordReCaptchaV2Invisible(@RequestParam("email") String email,
                                                     @RequestParam("g-recaptcha-response") String token,
                                                     RedirectAttributes redirectAttributes) {
        // TODO reCAPTCHA V2 invisible
        System.out.println(email);
        System.out.println(token);
        System.out.println(getReCaptchaVerifyResponse(reCaptchaProperty.getV2Invisible(), token));
        redirectAttributes.addFlashAttribute("message", "Email錯誤");
        return "redirect:/forget";
    }

    private ReCaptchaResponse getReCaptchaVerifyResponse(String secret, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("secret", secret);
        map.add("response", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<ReCaptchaResponse> response = new RestTemplate().postForEntity(reCaptchaProperty.getVerifyUrl(), request, ReCaptchaResponse.class);
        return response.getBody();
    }

    private void sendResetPasswordEmail(String email) {
        // TODO 重設密碼前要先產生UUID存入DB並產生專用URL
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("重設密碼");
        msg.setText("點此連結前往重設密碼頁面\nhttps://www.google.com");
        javaMailSender.send(msg);
    }

}