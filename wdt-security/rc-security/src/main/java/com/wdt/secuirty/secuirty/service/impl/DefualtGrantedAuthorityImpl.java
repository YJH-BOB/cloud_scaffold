package com.wdt.secuirty.secuirty.service.impl;

import org.springframework.security.core.GrantedAuthority;

public class DefualtGrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public DefualtGrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
