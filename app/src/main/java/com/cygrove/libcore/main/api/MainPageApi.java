package com.cygrove.libcore.main.api;

import com.cygrove.libcore.main.entity.HousingEstate;
import com.google.gson.JsonObject;
import com.xiongms.libcore.bean.BaseBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 *
 */
public interface MainPageApi {
    @POST("/v1/community/getall")
    Observable<BaseBean<List<HousingEstate>>> getall(@Body JsonObject json);
}