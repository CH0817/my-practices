package com.rex.my.web.controller.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockSecuredUser {

    String id() default "a";

    String email() default "test@email.com";

    String password() default "11111111";

}
