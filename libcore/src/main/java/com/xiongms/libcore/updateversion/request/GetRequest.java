package com.xiongms.libcore.updateversion.request;

import com.qiangxi.checkupdatelibrary.constants.Const;
import com.xiongms.libcore.updateversion.callback.BaseCallback;

import java.util.Map;

public class GetRequest extends AbsStringRequest {

    public GetRequest(String url) {
        super(url);
    }

    public GetRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    public GetRequest(String url, Map<String, String> params, BaseCallback callback) {
        super(url, params, callback);
    }

    @Override
    String requestMethod() {
        return Const.GET;
    }

}