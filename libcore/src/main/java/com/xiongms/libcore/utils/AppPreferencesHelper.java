package com.xiongms.libcore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

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

    public String getToken() {
        User user = getUser();
        return user.getTkn();
    }


    public void removeAll() {
        mPrefs.edit().clear().apply();
    }
}