package com.cygrove.libcore.register.contract;

import com.xiongms.libcore.mvp.IPresenter;
import com.xiongms.libcore.mvp.IView;

public interface Contract {
    interface View extends IView {
        String getPhone();

        void showEmptyView();

        void showNomalView();
    }

    interface Persenter extends IPresenter<View> {
        void getCode();

        void clickShowEmpty();

        void clickRefrsh();
    }
}