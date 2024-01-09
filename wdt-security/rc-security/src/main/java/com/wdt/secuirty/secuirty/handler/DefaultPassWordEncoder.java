package com.wdt.secuirty.secuirty.handler;

import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.common.utils.AESUtil;
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
        try {
            return AESUtil.encrypt(rawPassword.toString());
        } catch (Exception e) {
            throw new BusinessException(CodeEnum.DATA_ERROR);
        }
    }

    /***
     * 密码比对
     * @param rawPassword  传入密码
     * @param encodedPassword 原始密码
     * @return boolean
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return encodedPassword.equals(AESUtil.encrypt(rawPassword.toString()));
        } catch (Exception e) {
            throw new BusinessException(CodeEnum.DATA_ERROR);
        }
    }
}
