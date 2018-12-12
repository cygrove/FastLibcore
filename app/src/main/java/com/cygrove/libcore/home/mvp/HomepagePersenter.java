package com.cygrove.libcore.home.mvp;

import com.cygrove.libcore.mvp.BasePresenter;

import javax.inject.Inject;

public class HomepagePersenter extends BasePresenter<Contract.View> implements Contract.Persenter {
    @Inject
    public HomepagePersenter() {
    }

    @Override
    public void tabChanage(int index) {
        mRootView.showToast("这是第" + index + "个");
    }

    @Override
    public void fragmentClick(String msg) {
        mRootView.showToast(msg);
    }
}