package com.cygrove.libcore.qrcode.mvp;

import com.cygrove.libcore.mvp.IPresenter;
import com.cygrove.libcore.mvp.IView;

public interface Contract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
    }
}