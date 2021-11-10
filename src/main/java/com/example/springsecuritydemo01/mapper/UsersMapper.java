package com.example.springsecuritydemo01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springsecuritydemo01.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//@Repository
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
}
