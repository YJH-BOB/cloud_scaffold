package com.wdt.system.module.user.controller;


import com.wdt.system.module.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService userService;


    @PostMapping ("test")
    public String tset() {
        return "Hello";
    }
}