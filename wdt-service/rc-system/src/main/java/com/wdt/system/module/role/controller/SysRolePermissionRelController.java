package com.wdt.system.module.role.controller;

import com.wdt.system.module.role.service.SysRolePermissionRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rolePermissionRel")
public class SysRolePermissionRelController {
    @Autowired
    private SysRolePermissionRelService sysRolePermissionRelService;
}
