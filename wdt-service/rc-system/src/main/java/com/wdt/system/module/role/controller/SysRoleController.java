package com.wdt.system.module.role.controller;

import com.wdt.common.log.Log;
import com.wdt.common.model.Result;
import com.wdt.system.module.role.entity.SysRole;
import com.wdt.system.module.role.service.SysRoleService;
import com.wdt.system.module.user.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    
    @Log("根据用户id查询角色")
    @PostMapping("findByUserId")
    public Result<List<SysRole>> findByUserId(@RequestParam(value = "userId") Long userId) {
        return Result.succeed(sysRoleService.findByUserId(userId));
    }



}
