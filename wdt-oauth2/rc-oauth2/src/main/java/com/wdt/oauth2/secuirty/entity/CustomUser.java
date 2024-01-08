package com.wdt.oauth2.secuirty.entity;

import com.wdt.oauth2.module.user.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * Description:
 * Date: 2024/1/8
 * @author admin
 */

public class CustomUser extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1455525934466035301L;

    private SysUser sysUser ;

    public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUserName(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

}
