package com.cygrove.libcore.register.api;

import com.cygrove.libcore.register.HousingEstate;
import com.xiongms.libcore.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RegisterApi {
    @GET("/api/v1/app/regist/getValidCode")
    Observable<BaseBean<HousingEstate>> getCode(@Query("account") String phone);
}