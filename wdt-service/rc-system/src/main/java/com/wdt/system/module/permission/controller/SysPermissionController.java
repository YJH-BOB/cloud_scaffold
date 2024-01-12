package com.wdt.system.module.permission.controller;


import com.wdt.system.module.permission.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;


}
