package com.crazy.cc.framework.config;

import io.swagger.models.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //跨域方式3:
        http.requestMatchers()
                .antMatchers(String.valueOf(HttpMethod.OPTIONS), "/oauth/**")
                .and()
                .csrf()
                .disable()
                .formLogin()
                .and()
                .cors();
    }
}
