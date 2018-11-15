package com.xiongms.libcore.secret;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/*
 * MD5的算法在RFC1321 中定义
 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 */

public class Md5Util {
    public static String getMD5(String string) {
        String s = null;

        try {
            byte[] source = string.getBytes();
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(source);
            return byteToHexString(md.digest());
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return s;
    }

    public static byte[] getMD5(byte[] src) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(src);
            return md.digest();
        } catch (Exception e) {
            Logger.e(e.getMessage());

        }
        return null;
    }

    /**
     * 将字节型数据转化为16进制字符串
     */
    public static String byteToHexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0)
            return null;

        StringBuilder buf = new StringBuilder(bytes.length * 2);

        for (byte aByte : bytes) {
            if ((aByte & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(aByte & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * 对一个文件求他的md5值
     *
     * @param f 要求md5值的文件
     * @return md5串
     */

    public static String getMD5(File f) {
        FileInputStream fis = null;
        try {
            java.security.MessageDigest md = null;

            try {
                md = java.security.MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                Logger.e(e.getMessage());

            }
            fis = new FileInputStream(f);
            byte[] buffer = new byte[8192];
            int length;

            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }

            return byteToHexString(md.digest());
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                Logger.e(e.getMessage());

            }
        }
    }
}
