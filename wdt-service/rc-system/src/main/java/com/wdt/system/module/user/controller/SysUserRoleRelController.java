package com.wdt.system.module.user.controller;

import com.wdt.system.module.user.service.SysUserRoleRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysUserRoleRel")
public class SysUserRoleRelController {
    @Autowired
    private SysUserRoleRelService sysUserRoleRelService;


}
