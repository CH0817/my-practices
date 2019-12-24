package com.rex.my.web.controller.base;

import com.rex.my.security.model.SecuredUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SecuredUser getSecuredUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecuredUser) authentication.getPrincipal();
    }

    protected String getUserId() {
        return getSecuredUser().getId();
    }

}
