package com.example.springsecuritydemo01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

// WebSecurityConfigurerAdapter继承这个类
@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    // SpringSecurity的token的实现，注入数据源
    @Autowired
   private DataSource dataSource;



    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
//        // 给JdbcTokenRepositoryImpl一个datasource
//        jdbcTokenRepository.setDataSource(dataSource);
//
//
//
//        return jdbcTokenRepository;
        RedisTokenRepositoryImpl redisTokenRepository=new RedisTokenRepositoryImpl();
        return redisTokenRepository;
    }

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

    //
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 退出的配置
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();


        // 配置没有权限访问的配置
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin() // 跳到我们自定义的登录页面
                .loginPage("/login.html") // 登录页面设置
                .loginProcessingUrl("/user/login") //登录访问路径
                .defaultSuccessUrl("/sucess.html").permitAll() // 登录成功后跳转的页面
                .and().authorizeRequests()
               // .antMatchers("/test/hello","/user/login").permitAll()//访问这里配置的路径不需要登录，可以直接访问
                //方法2 这句话的意思是当前的test/index需要有admin权限才能登录
              //  .antMatchers("/test/index","/test/goods").hasAnyAuthority("admin,user")
                //方法3
                .antMatchers("/test/index").hasAnyRole("sale,admin")

                .anyRequest().authenticated()
               .and().rememberMe().tokenRepository(new JdbcTokenRepositoryImpl())

                .tokenValiditySeconds(600) // 设置有效时长
                .userDetailsService(userDetailsService)


                .and().csrf().disable(); //关闭csrf防护

    }
}
