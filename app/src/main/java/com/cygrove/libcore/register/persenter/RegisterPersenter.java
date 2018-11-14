package com.cygrove.libcore.register.persenter;

import android.Manifest;

import com.cygrove.libcore.main.entity.HousingEstate;
import com.cygrove.libcore.register.api.RegisterApi;
import com.cygrove.libcore.register.contract.Contract;
import com.google.gson.JsonObject;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.mvp.BasePresenter;
import com.xiongms.libcore.network.rx.RxResultHelper;
import com.xiongms.libcore.network.rx.RxResultSubscriber;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class RegisterPersenter extends BasePresenter<Contract.View> implements Contract.Persenter {
    private RegisterApi api;
    @Inject
    public Retrofit retrofit;

    @Inject
    public RegisterPersenter() {
        super();
    }

    @Override
    public void onAttach(Contract.View rootView) {
        super.onAttach(rootView);
        api = retrofit.create(RegisterApi.class);
    }

    @Override
    public void getCode() {
        RxResultHelper.getHttpObservable(mRootView.getContext(), api.getCode(mRootView.getPhone())).subscribe(new RxResultSubscriber<HousingEstate>() {
            @Override
            public void start() {
                mRootView.showLoading(true);
            }

            @Override
            public void error(String code, String msg) {
                mRootView.hideLoading();
                mRootView.showToast(msg);
            }


            @Override
            public void success(BaseBean<HousingEstate> t) {
                mRootView.hideLoading();
            }
        });
    }

    @Override
    public void clickShowEmpty() {
        mRootView.showEmptyView();
    }

    @Override
    public void clickRefrsh() {
        mRootView.showNomalView();
    }
}