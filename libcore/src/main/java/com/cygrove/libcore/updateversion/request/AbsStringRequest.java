package com.cygrove.libcore.updateversion.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.qiangxi.checkupdatelibrary.constants.Const;
import com.cygrove.libcore.updateversion.annotation.RequestType;
import com.cygrove.libcore.updateversion.callback.BaseCallback;
import com.cygrove.libcore.updateversion.callback.StringCallback;
import com.cygrove.libcore.updateversion.dispatcher.CallbackDispatcher;
import com.cygrove.libcore.updateversion.exception.CallbackException;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

abstract class AbsStringRequest implements IRequest {

    private String mUrl;//请求地址
    private Map<String, String> mParams;//附加参数
    private StringCallback mCallback;//请求回调

    AbsStringRequest(@NonNull String url) {
        mUrl = url;
    }

    AbsStringRequest(@NonNull String url, @Nullable Map<String, String> params) {
        mUrl = url;
        mParams = params;
    }

    AbsStringRequest(@NonNull String url, @Nullable Map<String, String> params, @Nullable BaseCallback callback) {
        mUrl = url;
        mParams = params;
        //检查callback类型
        if (callback != null && !(callback instanceof StringCallback))
            throw new CallbackException("get or post request must be use StringCallback");
        mCallback = (StringCallback) callback;
    }

    /**
     * 由子类复写，返回请求的方式，必须使用RequestType注解允许的值
     *
     * @return 请求方式，Const.GET or Const.POST
     */
    @RequestType
    abstract String requestMethod();

    @Override
    public void request() {
        CallbackDispatcher.dispatchRequestStart(mCallback);
        String requestMethod = requestMethod();
        OkHttpClient client = new OkHttpClient();
        //构建Request
        Request.Builder builder = new Request.Builder();
        //拼接参数
        addParams(requestMethod, builder);
        Request request = builder.build();
        //发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                Logger.e(e.getMessage());
                CallbackDispatcher.dispatchRequestFailure(mCallback, e);
                CallbackDispatcher.dispatchRequestFinish(mCallback);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Logger.e(response.toString());
                CallbackDispatcher.dispatchRequestSuccess(mCallback, response);
                CallbackDispatcher.dispatchRequestFinish(mCallback);
            }
        });
    }

    private void addParams(String requestMethod, Request.Builder builder) {
        if (mParams != null) {
            Set<Map.Entry<String, String>> entries = mParams.entrySet();
            if (Const.GET.equals(requestMethod)) {
                StringBuilder sb = new StringBuilder();
                sb.append(mUrl).append("?");
                for (Map.Entry<String, String> param : entries)
                    sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
                mUrl = sb.toString();
            } else if (Const.POST.equals(requestMethod)) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                for (Map.Entry<String, String> param : entries)
                    formBodyBuilder.add(param.getKey(), param.getValue());
                builder.method(requestMethod, formBodyBuilder.build());
            }
        }
        Logger.e(mUrl);
        builder.url(mUrl);
    }
}