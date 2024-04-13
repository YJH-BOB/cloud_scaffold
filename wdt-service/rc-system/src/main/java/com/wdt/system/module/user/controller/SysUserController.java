package com.wdt.system.module.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.common.log.Log;
import com.wdt.common.model.Result;
import com.wdt.system.module.user.entity.SysUser;
import com.wdt.system.module.user.service.SysUserService;
import jakarta.validation.constraints.Pattern;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Validated
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @PostMapping("test")
    public String tset() {
        return "Hello";
    }

    @Log("根据用户名查询用户-远程调用接口")
    @PostMapping("findByUserName")
    public JSONObject findByUserName(@RequestParam(value = "userName") String userName) {
        return (JSONObject) JSONObject.toJSON(userService.findByUserName(userName));
    }



    @Log("实习生写的代码")
    @PostMapping("test")
    public Result<List<SysUser>> test(@RequestParam("gender") @Pattern(regexp = "[01]", message = "参数错误") String gender) {
        return Optional.ofNullable(userService.list()).map(userList -> {
            userList.stream().forEach(user -> {
                if (gender.equals(user.getSex())) {
                    userList.remove(user);
                }
            });
            return Result.succeed(userList);
        }).orElseThrow(() -> {
            throw new BusinessException(CodeEnum.DATA_NOT_FOUND);
        });
    }




}
