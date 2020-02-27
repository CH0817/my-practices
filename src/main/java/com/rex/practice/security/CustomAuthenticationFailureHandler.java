package com.rex.practice.security;

import com.rex.practice.model.message.ErrorMessage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自訂登入失敗處理
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws ServletException, IOException {
        request.setAttribute("message", new ErrorMessage(exception.getMessage(), "/login"));
        request.getRequestDispatcher("helper/show/info").forward(request, response);
    }

}
