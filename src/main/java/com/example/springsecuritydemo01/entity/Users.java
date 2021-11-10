package com.example.springsecuritydemo01.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("myuser")
public class Users {
    private Integer id;
    private String username;
    private String password;
}
