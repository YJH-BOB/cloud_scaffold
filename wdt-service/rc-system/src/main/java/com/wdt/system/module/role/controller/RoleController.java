package com.wdt.system.module.role.controller;

import com.wdt.system.module.role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private SysRoleService sysRoleService;


}
