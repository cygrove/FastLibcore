package com.xiongms.libcore.network.rx;


import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.network.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @author cygrove
 * @time 2018-08-17 11:59
 */
public abstract class RxResultSubscriber<T> implements Observer<BaseBean<T>> {

    private String msg;

    private String code;

    @Override
    public void onNext(BaseBean<T> t) {
        if (t.getCode().equals("OK")) {
            try {
                success(t);
            } catch (Exception e) {
                e.printStackTrace();
                code = t.getCode();
                msg = "数据加载异常";
                error(code, msg);
            }
        } else {
            code = t.getCode();
            msg = t.getMessage();
            error(code, msg);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        ApiException apiException = RxUtils.getResultException(e);
        error(apiException.getStrCode(), apiException.getMessage());
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        start();
    }

    public abstract void start();

    public abstract void error(String code, String msg);

    public abstract void success(BaseBean<T> t);
}
