package com.cygrove.libcore.secret;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSAUtil （RSA加密算法）
 *
 * @author lixh
 */
public class RSAUtil {
    private static final String pbkey = "";

    /**
     * 加密的方法
     */
    @SuppressLint("TrulyRandom")
    public static String encrypt(String source) throws Exception {
        byte[] keyByte = Base64.decode(pbkey, Base64.DEFAULT);
        X509EncodedKeySpec x509ek = new X509EncodedKeySpec(keyByte);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509ek);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] sbt = source.getBytes();
        byte[] epByte = cipher.doFinal(sbt);
        return Base64.encodeToString(epByte, Base64.DEFAULT);
    }
}
