package com.example.springsecuritydemo01.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("myuser")
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Integer id;
    private String username;
    private String password;

//    public Users(Integer id, String username, String password) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//    }
}
