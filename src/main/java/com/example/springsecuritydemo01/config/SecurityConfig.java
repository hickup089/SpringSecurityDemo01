package com.example.springsecuritydemo01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// WebSecurityConfigurerAdapter继承这个类
//@Configuration 暂时注释
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 重写这个configure方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // security需要对密码进行加密，用security的加密方法
        BCryptPasswordEncoder bpe=new BCryptPasswordEncoder();
        String password=bpe.encode("admin");
        // 放到内存中,分别是用户名，密码，角色
       auth.inMemoryAuthentication().withUser("admin").password(password).roles("admin");
       // java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
    }


    // 这里的原因是PasswordEncoder需要有一个实例化
    @Bean
    PasswordEncoder password(){
        // 这里PasswordEncoder是一个接口，所以只能返回他的实现类
        return new BCryptPasswordEncoder();
    }
}
