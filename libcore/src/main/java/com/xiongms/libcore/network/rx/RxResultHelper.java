package com.xiongms.libcore.network.rx;


import android.content.Context;

import com.google.gson.Gson;
import com.xiongms.libcore.BaseApplication;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.network.exception.ApiException;
import com.xiongms.libcore.network.exception.ExceptionCont;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

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