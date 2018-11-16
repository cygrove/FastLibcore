package com.cygrove.libcore.news.api;

import com.cygrove.libcore.news.bean.NewsEntry;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.bean.BasePageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("/api/v1/app/user/message/getSystemMsgList")
    Observable<BaseBean<BasePageBean<NewsEntry>>> reqData(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
}