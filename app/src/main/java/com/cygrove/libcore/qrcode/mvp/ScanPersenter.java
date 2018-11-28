package com.cygrove.libcore.qrcode.mvp;

import com.xiongms.libcore.mvp.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class ScanPersenter extends BasePresenter<Contract.View> implements Contract.Presenter {
    @Inject
    public ScanPersenter(Retrofit retrofit) {
    }

    @Override
    public void onAttach(Contract.View rootView) {
        super.onAttach(rootView);
    }
}