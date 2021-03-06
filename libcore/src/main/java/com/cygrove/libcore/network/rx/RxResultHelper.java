package com.cygrove.libcore.network.rx;


import android.content.Context;

import io.reactivex.Observable;

/**
 * @author cygrove
 * @time 2018-11-15 11:57
 */
public class RxResultHelper {
    /**
     * @param observable
     * @param <R>        处理请求进行转化,返回http结果
     */
    public static <R> Observable<R> getHttpObservable(Context context, Observable<R> observable) {
        return observable
                .compose(RxUtils.<R>schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(context))
                .compose(RxUtils.<R>handleResult())
                .retryWhen(RxUtils.handleRetryWhen());
    }
}