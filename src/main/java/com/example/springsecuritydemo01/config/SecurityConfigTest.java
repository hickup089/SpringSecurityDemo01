package com.example.springsecuritydemo01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// WebSecurityConfigurerAdapter继承这个类
@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    // 重写这个configure方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 注入userDetailsService，然后把password的实例传进去
     auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }
    // 这里的原因是PasswordEncoder需要有一个实例化
    @Bean
    PasswordEncoder password(){
        // 这里PasswordEncoder是一个接口，所以只能返回他的实现类
        return new BCryptPasswordEncoder();
    }
}
