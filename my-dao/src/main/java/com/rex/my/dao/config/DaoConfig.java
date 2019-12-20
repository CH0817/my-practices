package com.rex.my.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.rex.my.dao")
@PropertySource(value = "classpath:devDB.properties", ignoreResourceNotFound = true, encoding = "utf-8")
@PropertySource(value = "classpath:devProd.properties", ignoreResourceNotFound = true, encoding = "utf-8")
public class DaoConfig {}
