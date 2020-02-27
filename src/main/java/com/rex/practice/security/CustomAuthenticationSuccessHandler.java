package com.rex.practice.security;

import com.rex.practice.model.message.InfoMessage;
import com.rex.practice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自訂登入成功處理
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserService userService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        logger.info("{} login success", principal.getEmail());
        if (!userService.isEmailVerified(principal.getEmail())) {
            request.setAttribute("message", new InfoMessage("Email尚未驗證", "/main"));
            request.getRequestDispatcher("helper/show/info").forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/main");
        }
    }

}
