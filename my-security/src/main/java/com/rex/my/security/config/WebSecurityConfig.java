package com.rex.my.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ignoreH2Console(http);
        // 靜態資源
        http.authorizeRequests().antMatchers("/js/**", "/easy-ui-1.9.0/**").permitAll();
        // 不限制請求
        http.authorizeRequests().antMatchers("/login").permitAll();
        // role = USER 可使用
        http.authorizeRequests().antMatchers("/account-book/**", "/account/**", "/combobox/**", "/function/**", "/item/**").hasRole("USER");
        // 自定義登入
        http.formLogin().loginPage("/login")// 指定登入頁
                .usernameParameter("email")// username 與 html email input name 對應
                .passwordParameter("password")// password 與 html pwd input name 對應
                .defaultSuccessUrl("/main")// 驗證成功轉跳頁面，不能與 successForwardUrl、successHandler 共用
                .failureForwardUrl("/login-error");// 驗證失敗轉跳頁面
        // 所有請求均要被驗證，anyRequest()必須在最後否則antMatchers()會出錯
        // http.authorizeRequests().anyRequest().authenticated().and();
    }

    private void ignoreH2Console(HttpSecurity http) throws Exception {
        //允許同源使用 iframe，處理 H2 console 的 X-Frame-Options:DENY 問題
        http.headers().frameOptions().sameOrigin().and();
        //忽略 H2 console 的 CSRF 驗證
        http.csrf().ignoringAntMatchers("/h2-console/**").and();
        //H2 console 不須驗證
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
    }

}
