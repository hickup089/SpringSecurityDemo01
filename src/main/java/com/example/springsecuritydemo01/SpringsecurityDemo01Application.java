package com.example.springsecuritydemo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)  // 这个是开启Security的Secured注解
public class SpringsecurityDemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityDemo01Application.class, args);
    }

}
