package com.cygrove.libcore.register.api;

import com.cygrove.libcore.register.VersionInfo;
import com.xiongms.libcore.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CheckNewVersion {
    @GET("/api/v1/app/version/checkVersion")
    Observable<BaseBean<VersionInfo>> checkVersion();
}