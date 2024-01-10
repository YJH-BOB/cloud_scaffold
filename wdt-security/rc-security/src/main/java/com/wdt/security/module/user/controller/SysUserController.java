package com.wdt.security.module.user.controller;


import com.wdt.security.module.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class SysUserController {
    @Autowired
    private SysUserService userService;



}