package com.rex.my.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ignoreH2Console(http);
        http.authorizeRequests(requests -> requests.antMatchers("/").permitAll());
        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());
        // http.formLogin(form -> form.loginPage("/").permitAll());
    }

    private void ignoreH2Console(HttpSecurity http) throws Exception {
        //允許同源使用 iframe，處理 H2 console 的 X-Frame-Options:DENY 問題
        http.headers().frameOptions().sameOrigin().and()
                //忽略 H2 console 的 CSRF 驗證
                .csrf().ignoringAntMatchers("/h2-console/**").and();
        //H2 console 不須驗證
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/easy-ui-1.9.0/**"); // ignore files
        web.ignoring().antMatchers("/js/**"); // ignore files
    }

}
