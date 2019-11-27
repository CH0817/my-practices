package com.rex.my.web.controller;

import com.rex.my.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService service;
    private ApplicationContext context;

    @Autowired
    public UserController(UserService service, ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    @RequestMapping("/page")
    public String user() throws Exception {
        AtomikosDataSourceBean dataSource = (AtomikosDataSourceBean) context.getBean("primaryDataSource");
        System.out.println(dataSource.getMaxPoolSize());
        return "user";
    }

}
