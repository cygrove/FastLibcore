package com.cygrove.libcore.updateversion.request;

import com.qiangxi.checkupdatelibrary.constants.Const;
import com.cygrove.libcore.updateversion.callback.BaseCallback;

import java.util.Map;

public class PostRequest extends AbsStringRequest {

    public PostRequest(String url) {
        super(url);
    }

    public PostRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    public PostRequest(String url, Map<String, String> params, BaseCallback callback) {
        super(url, params, callback);
    }

    @Override
    String requestMethod() {
        return Const.POST;
    }

}
