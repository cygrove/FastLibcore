package com.xiongms.libcore.mvp;

import android.content.Context;

/**
 * @author cygrove
 * @time 2018-11-12 11:13
 */
public interface IView {
    Context getContext();

    void showLoading(boolean isDialog);

    void hideLoading();

    void showToast(String msg);
}