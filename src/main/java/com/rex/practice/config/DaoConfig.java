package com.rex.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:devDB.properties", ignoreResourceNotFound = true, encoding = "utf-8")
@PropertySource(value = "classpath:devProd.properties", ignoreResourceNotFound = true, encoding = "utf-8")
public class DaoConfig {}
