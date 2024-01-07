package com.wdt.oauth2.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdt.oauth2.module.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}