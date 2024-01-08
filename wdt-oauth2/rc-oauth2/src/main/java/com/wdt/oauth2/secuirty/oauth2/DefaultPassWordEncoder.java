package com.wdt.oauth2.secuirty.oauth2;

import cn.hutool.crypto.digest.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 默认的密码处理
 */
@Component
public class DefaultPassWordEncoder implements PasswordEncoder {
    /**
     *  密码加密
     * @param rawPassword
     * @return String
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.create().digestHex(rawPassword.toString());
    }

    /***
     * 密码比对
     * @param rawPassword  传入密码
     * @param encodedPassword 原始密码
     * @return boolean
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.create().digestHex(rawPassword.toString()));
    }
}
