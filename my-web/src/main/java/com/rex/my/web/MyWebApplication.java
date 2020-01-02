package com.rex.my.web;

import com.rex.my.business.config.BusinessConfig;
import com.rex.my.security.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {BusinessConfig.class, WebSecurityConfig.class})
public class MyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWebApplication.class);
    }

}
