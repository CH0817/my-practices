package com.rex.my.dao.config.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
// @PropertySource("classpath:mysql-config.properties")
// @ConfigurationProperties(prefix = "datasource.mysql")
public class MySqlProperties {

    private String driverClass;
    private String url;
    private String user;
    private String password;

}
