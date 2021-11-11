package com.example.springsecuritydemo01.config;

import com.example.springsecuritydemo01.SpringsecurityDemo01Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = SpringsecurityDemo01Application.class)
class RedisConfigurationTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void redisTemplate() {

        redisTemplate.opsForValue().set("a","b");
        log.info("获取redis:{}",redisTemplate.opsForValue().get("a"));
    }
}