package com.wdt.oauth2.module.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.oauth2.module.user.entity.User;
import com.wdt.oauth2.module.user.mapper.UserMapper;
import com.wdt.oauth2.module.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}