package com.wdt.security.security.service.impl;

import org.springframework.security.core.GrantedAuthority;

public class DefaultGrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public DefaultGrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
