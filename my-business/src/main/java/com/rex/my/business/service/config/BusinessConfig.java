package com.rex.my.business.service.config;

import com.rex.my.dao.config.DaoConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.rex.my.business"})
@Import(DaoConfig.class)
public class BusinessConfig {}
