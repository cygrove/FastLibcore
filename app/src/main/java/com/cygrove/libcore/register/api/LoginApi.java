package com.cygrove.libcore.register.api;


import com.cygrove.libcore.register.moudule.LoginMoudule;
import com.google.gson.JsonObject;
import com.xiongms.libcore.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/api/v1/app/login/pwdlogin")
    Observable<BaseBean<LoginMoudule>> getToken(@Body JsonObject json);
}