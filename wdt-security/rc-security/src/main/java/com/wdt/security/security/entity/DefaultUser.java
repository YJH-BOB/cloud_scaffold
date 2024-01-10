package com.wdt.security.security.entity;

import com.wdt.security.module.user.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class DefaultUser implements UserDetails , Serializable {

    private SysUser sysuser ;

    @Serial
    private static final long serialVersionUID = 8923840317501504008L;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysuser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysuser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }

    public SysUser getUserInfo() {
        return this.sysuser ;
    }

    public DefaultUser(SysUser sysuser, List<GrantedAuthority> authorities) {
        this.sysuser = sysuser;
        this.authorities = authorities;
    }
}
