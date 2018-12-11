package com.cygrove.libcore.register.contract;

import com.cygrove.libcore.register.moudule.LoginMoudule;
import com.xiongms.libcore.mvp.IPresenter;
import com.xiongms.libcore.mvp.IView;

public interface Contract {
    interface View extends IView {
        String getPhone();

        void showEmptyView();

        void showNomalView();

        void jump();

        void saveEntry(LoginMoudule moudule);
    }

    interface Persenter extends IPresenter<View> {

        void clickShowEmpty();

        void clickRefrsh();

        void cheakNewVersion();

        void getToken();

        void clickClearToken();
    }
}