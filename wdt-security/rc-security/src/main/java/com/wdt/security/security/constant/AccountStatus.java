package com.wdt.security.security.constant;

/**
 * @author admin
 * 账号状态枚举类
 */

public enum AccountStatus {
    ACTIVE("0"),
    INACTIVE("1");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}