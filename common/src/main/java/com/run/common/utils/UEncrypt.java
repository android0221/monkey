package com.run.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类 有AES加密 MD5加密
 */

public class UEncrypt {
    /**
     * 日志
     */
    public static final String TAG = UEncrypt.class.getSimpleName();

    /**
     * AES加密
     */
    public static String encrypt_AES(String input, String encryptKey) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(encryptKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {

        }
        return parseByte2HexStr(crypted); // 加密
    }

    /**
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toLowerCase());
        }
        return sb.toString();
    }
}
