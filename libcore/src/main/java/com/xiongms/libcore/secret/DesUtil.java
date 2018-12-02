package com.xiongms.libcore.secret;

import com.orhanobut.logger.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil {
    private static byte[] iv = {
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8};

    /**
     * 加密逻辑方法
     *
     * @param encryptString
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        // IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        //IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        return Base64.encode(encryptedData);
    }


    /**
     * 解密逻辑方法
     *
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString, String decryptKey) {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        // IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte decryptedData[] = cipher.doFinal(byteMi);

            return new String(decryptedData);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return "";
    }


    public static void main(String[] args) throws Exception {
        // String key = "YcV%fcKm";
        // String text = "你 好!中#国f";
        // String result1 = encryptDES(text, key);
        // String result2 = decryptDES(result1, key);
        // LogUtil.i(result1);
        // LogUtil.i(result2);
    }
}