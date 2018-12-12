package com.cygrove.libcore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.cygrove.libcore.di.qualifiers.ApplicationContext;
import com.cygrove.libcore.di.qualifiers.PreferenceInfo;

import javax.inject.Inject;

/**
 * @author cygrove
 * @time 2018-11-14 16:55
 */
public class AppPreferencesHelper {
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


    public synchronized void saveModel(Object object) {
        if (object != null) {
            String sInfo = GsonUtils.gsonToString(object);
            if (!TextUtils.isEmpty(sInfo)) {
                mPrefs.edit().putString(object.getClass().getSimpleName(), sInfo).apply();
            }
        }
    }

    public synchronized <T> T getModel(Class<T> classz) {
        String sInfo = mPrefs.getString(classz.getSimpleName(), "");
        if (!TextUtils.isEmpty(sInfo)) {
            return mGson.fromJson(sInfo, classz);
        }
        return null;
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

    public void removeAll() {
        mPrefs.edit().clear().apply();
    }
}