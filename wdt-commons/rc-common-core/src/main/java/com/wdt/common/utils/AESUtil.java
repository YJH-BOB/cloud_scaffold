package com.wdt.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Slf4j
public class AESUtil {
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION_CBC = "AES/CBC/PKCS5Padding";

    private static final String IV = "0123456789ABCDEF"; // 固定的IV
    private static final String KEY = "ABCDEFGHIJKLMNOP"; // 固定的密钥

    public static String encrypt(String data) throws Exception {
        byte[] encryptedBytes = doEncrypt(data.getBytes(StandardCharsets.UTF_8), KEY.getBytes(StandardCharsets.UTF_8), IV.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        byte[] decryptedBytes = doDecrypt(Base64.getDecoder().decode(encryptedData), KEY.getBytes(StandardCharsets.UTF_8), IV.getBytes(StandardCharsets.UTF_8));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static byte[] doEncrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION_CBC);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    private static byte[] doDecrypt(byte[] encryptedData, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION_CBC);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
        return cipher.doFinal(encryptedData);
    }
    public static void main(String args[]) throws Exception {
        String encrypt = encrypt("123");
        System.out.println(encrypt);
        log.info("11111"+ encrypt);
    }
}
