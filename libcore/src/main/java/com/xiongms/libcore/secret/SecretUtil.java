package com.xiongms.libcore.secret;


import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加密工具 Created by Administrator on 2014/10/28.
 */
public class SecretUtil {
    /**
     * 从32位md5加密值中抽取8位字符用到的key
     */
    private static String DEFUALT_EXTRACT_KEY = "3,31,22,14,18,8,26,11";

    /**
     * 过滤空、特殊参数
     *
     * @param param
     * @return
     */
    public static Map<String, String> filterParam(Map<String, String> param) {
        Map<String, String> result = new HashMap<>();
        if (param == null || param.size() == 0) {
            return result;
        }

        for (String key : param.keySet()) {
            String value = param.get(key);
            if (value != null && !"".equals(value) && !"token".equals(key)) {
                result.put(key, value);
            }

        }
        return result;
    }

    /**
     * 拼装参数字符串
     *
     * @param param
     * @param key
     * @return
     */
    public static String createLinkStr(Map<String, Object> param, String key) throws Exception {
        List<String> keys = new ArrayList<>(param.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            Object value = param.get(keys.get(i));
            sb.append(encrypt((String) value, key));
        }
        return sb.toString();
    }

    /**
     * 取得截位的token
     *
     * @param str
     * @return
     */
    private static String splitToken(String str) {
        String[] nums = DEFUALT_EXTRACT_KEY.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : nums) {
            int num = Integer.valueOf(s);
            sb.append(str.substring(num - 1, num));
        }
        return sb.toString();
    }

    /**
     * 对字符串进行des加密
     *
     * @param message
     * @param key
     * @return
     */
    public static String encrypt(String message, String key) {
        if (key != null) {
            try {
                return DesUtil.encryptDES(message, key);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 对字符串进行des解密
     *
     * @param message
     * @param key
     * @return
     */
    public static String decrypt(String message, String key) {
        if (key != null && message != null) {
            try {
                return DesUtil.decryptDES(message, key);
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }
        return null;
    }


    public static List<String> sortMapKey(List<String> keyList) {
        List<String> sortList = new ArrayList<>();
        if (keyList == null || keyList.size() == 0) {
            return sortList;
        }

        for (int x = 0; x < keyList.size(); ) {
            String minValue = keyList.get(0);
            for (int y = 1; y < keyList.size(); y++) {
                if (minValue.compareTo(keyList.get(y)) >= 0) {
                    minValue = keyList.get(y);
                }
            }
            sortList.add(minValue);
            keyList.remove(minValue);
        }
        return sortList;
    }

    public static String getDesSecret(String s, String desKey) {
        return SecretUtil.encrypt(s, desKey);
    }
}