package com.example.springsecuritydemo01.controller;

import com.example.springsecuritydemo01.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

   // @Secured({"ROLE_sale","ROLE_manager"}) // 第一个方法
   // @PreAuthorize("hasAnyAuthority('admins')") // 注意这个的写法，外边加"" 第二个方法
    @PostAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/update")
    public String update(){
        log.info("我是update，我权限是admin");
        return "hello update";
    }

    @PostAuthorize("hasAnyAuthority('admins')") // PostAuthorize这个是执行方法之后校验
    @GetMapping("/updates")
    public String updates(){
        log.info("我是updates，我权限是admins");
        return "hello update";
    }

    @PostAuthorize("hasAnyAuthority('admins')") // PostAuthorize这个是执行方法之后校验
    @PostFilter("filterObject.username=='admin1'") // 过滤其他数据，只返回的object里面的username为admin1的拦截
    @GetMapping("/postfilter")
    public List<Users> ListTest(){
        List<Users> list=new ArrayList<>();
        list.add(new Users(Integer.valueOf(1),"admin","admin"));
        list.add(new Users(Integer.valueOf(11),"admin1","admin1"));
        list.add(new Users(Integer.valueOf(111),"admin2","admin2"));
        log.info("我是List:{}",list);
        return list;
    }
}
