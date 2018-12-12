package com.cygrove.libcore.updateversion.callback;

public interface StringCallback extends BaseCallback {
    /**
     * 检查更新接口请求成功回调
     *
     * @param result 接口返回的数据，考虑到返回结果可能为json也可能为xml，故交给开发者自己去解析处理
     */
    void checkUpdateSuccess(String result);
}