package com.xiongms.libcore.secret;


import com.orhanobut.logger.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5Encrypt MD5加密类
 */
public class MD5Encrypt {
    public static String encrypt(String content) {
        try {
            if (content != null && content.length() > 0) {
                return encode(content, "MD5");
            } else {
                return "";
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.e(e.getMessage());
        }
        return "";
    }

    private static String encode(String _password, String _algorithm) throws NoSuchAlgorithmException {
        String password = _password;

        byte[] defaultBytes = password.getBytes();
        MessageDigest algorithm = MessageDigest.getInstance(_algorithm);
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String hex = Integer.toHexString(0xff & aMessageDigest);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
