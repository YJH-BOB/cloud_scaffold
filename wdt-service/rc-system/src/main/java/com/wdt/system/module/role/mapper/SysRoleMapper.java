package com.wdt.system.module.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdt.system.module.role.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> findByUserId(@Param(value = "userId") Long userId);
}
