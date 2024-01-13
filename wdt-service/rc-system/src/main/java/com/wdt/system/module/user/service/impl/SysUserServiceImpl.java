package com.wdt.system.module.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.system.module.user.entity.SysUser;
import com.wdt.system.module.user.service.SysUserService;
import com.wdt.system.module.user.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser findByUserName(String userName) {
        try {
            return this.getOneOpt(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, userName)).orElseThrow(() -> new BusinessException(CodeEnum.USER_NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException("查询一条数据返回了多行");
        }
    }
}