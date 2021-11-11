package com.example.springsecuritydemo01.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springsecuritydemo01.entity.Users;
import com.example.springsecuritydemo01.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// 实现UserDetailsService接口,这里的Service名字要和SecurityConfigTest里面导入的一样
@Service("UserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名，调用usermapper的方法查询用户
        QueryWrapper<Users> wrapper=new QueryWrapper<>();
        // 这里相当于在sql里加了一个查询条件。where username='username'
        wrapper.eq("username",username);
        Users users= usersMapper.selectOne(wrapper);

        // 逻辑判断
        if (users==null){// 数据库没有值，有问题，给抛异常,认证失败
            throw new UsernameNotFoundException("用户名不存在!");
        }

        // 这里是权限角色的list，对应的用户有对应的权限，这里我们虚拟一个admin角色出来
        List<GrantedAuthority> auth= AuthorityUtils.commaSeparatedStringToAuthorityList("admin2");

        // 从查询的数据库的用户对象里面拿到
        return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auth);
    }
}
