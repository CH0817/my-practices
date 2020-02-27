package com.rex.practice.web.controller.base;

import com.rex.practice.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected CustomUserDetails getSecuredUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    protected String getUserId() {
        return getSecuredUser().getId();
    }

}
