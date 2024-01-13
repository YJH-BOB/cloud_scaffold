package com.wdt.system.module.user.controller;

import com.wdt.common.log.Log;
import com.wdt.common.model.Result;
import com.wdt.system.module.user.entity.SysUser;
import com.wdt.system.module.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Log("根据用户名查询用户")
    @PostMapping ("findByUserName")
    public Result<SysUser> findByUserName(@RequestParam(value = "userName") String userName) {
        return Result.succeed (userService.findByUserName(userName));
    }


}