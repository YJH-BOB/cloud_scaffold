package com.wdt.system.module.role.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wdt.common.log.Log;
import com.wdt.common.model.Result;
import com.wdt.system.module.role.entity.SysRole;
import com.wdt.system.module.role.service.SysRoleService;
import com.wdt.system.module.user.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Log("根据用户id查询角色-远程调用接口")
    @PostMapping("findByUserId")
    public JSONArray findByUserId(@RequestParam(value = "userId") Long userId) {
        return sysRoleService.findByUserId(userId).stream()
                .map(sysRole -> {
                    JSONObject role = new JSONObject();
                    role.put("roleName", sysRole.getRoleName());
                    return role;
                })
                .collect(Collectors.toCollection(JSONArray::new));
    }



}
