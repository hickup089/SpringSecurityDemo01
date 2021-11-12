package com.example.springsecuritydemo01.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

//@Component
public class  RedisUtil  {

    @Resource
    private RedisTemplate<String,Object> redis;

    @Autowired
    public static RedisTemplate redisTemplate;

    @PostConstruct
    public void redisTemplate(){
        redisTemplate=  this.redis;
    }

}
