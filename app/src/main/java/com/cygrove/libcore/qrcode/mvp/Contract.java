package com.cygrove.libcore.qrcode.mvp;

import com.xiongms.libcore.mvp.IPresenter;
import com.xiongms.libcore.mvp.IView;

public interface Contract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
    }
}