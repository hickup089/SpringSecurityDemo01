package com.example.springsecuritydemo01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String add(){
        return "hello security";
    }


    @GetMapping("/index")
    public String index(){
        return "hello index";
    }

    @GetMapping("/goods")
    public String goods(){
        return "hello goods";
    }
}
