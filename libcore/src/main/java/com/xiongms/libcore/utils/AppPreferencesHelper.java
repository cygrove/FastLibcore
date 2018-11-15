package com.xiongms.libcore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.xiongms.libcore.bean.User;
import com.xiongms.libcore.di.qualifiers.ApplicationContext;
import com.xiongms.libcore.di.qualifiers.PreferenceInfo;

import javax.inject.Inject;

/**
 * @author cygrove
 * @time 2018-11-14 16:55
 */
public class AppPreferencesHelper {
    public final static String KEY_SP_USER_PHONE = "key_sp_user_phone";

    public final static String KEY_SP_USER_INFO = "key_sp_user_info";

    public final static String KEY_SP_TOKEN = "key_sp_token";

    private final SharedPreferences mPrefs;

    private final Gson mGson;

    @Inject
    AppPreferencesHelper(@ApplicationContext Context context,
                         @PreferenceInfo String prefFileName,
                         final @NonNull Gson gson) {
        mGson = gson;
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    public void setUserPhone(String phone) {
        mPrefs.edit().putString(KEY_SP_USER_PHONE, phone).apply();
    }

    public String getUserPhone() {
        return mPrefs.getString(KEY_SP_USER_PHONE, "");
    }

    public void setToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            mPrefs.edit().putString(KEY_SP_TOKEN, token).apply();
        }
    }

    public String getToken() {
        String token = mPrefs.getString(KEY_SP_TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            return token;
        }
        return "";
    }

    public void setUser(User userInfo) {
        if (userInfo != null) {
            setUserPhone(userInfo.getPhone());
        }
        String sUserInfo = mGson.toJson(userInfo);
        mPrefs.edit().putString(KEY_SP_USER_INFO, sUserInfo).apply();
    }

    public User getUser() {
        String sUser = mPrefs.getString(KEY_SP_USER_INFO, "");
        User user = mGson.fromJson(sUser, User.class);
        if (user == null)
            user = new User();
        return user;
    }

    public void removeAll() {
        mPrefs.edit().clear().apply();
    }
}