package com.cygrove.libcore.home.mvp;

import com.xiongms.libcore.mvp.IPresenter;
import com.xiongms.libcore.mvp.IView;

public interface Contract {
    interface View extends IView {
    }

    interface Persenter extends IPresenter<View> {
        void tabChanage(int index);

        void fragmentClick(String msg);
    }
}