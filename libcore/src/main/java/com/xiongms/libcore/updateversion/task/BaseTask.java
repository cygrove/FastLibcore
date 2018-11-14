package com.xiongms.libcore.updateversion.task;


import com.xiongms.libcore.updateversion.callback.BaseCallback;

import java.util.Map;


abstract class BaseTask<T extends BaseTask> {
    protected String mUrl;//接口地址or下载地址
    protected Map<String, String> mParams;
    protected BaseCallback mCallback;

    BaseTask(String url) {
        mUrl = url;
    }

    BaseTask(String url, Map<String, String> params) {
        mUrl = url;
        mParams = params;
    }

    public T callback(BaseCallback callback) {
        mCallback = callback;
        return (T) this;
    }

    /**
     * 执行任务
     */
    public abstract void execute();

}