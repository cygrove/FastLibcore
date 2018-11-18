package com.cygrove.libcore.main.persenter;

import com.cygrove.libcore.main.api.MainPageApi;
import com.cygrove.libcore.main.contract.Contract;
import com.cygrove.libcore.main.entity.HousingEstate;
import com.google.gson.JsonObject;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.env.Environment;
import com.xiongms.libcore.mvp.BasePresenter;
import com.xiongms.libcore.network.rx.RxResultHelper;
import com.xiongms.libcore.network.rx.RxResultSubscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 *
 */
public class MainPresenter extends BasePresenter<Contract.View> implements Contract.Presenter {

    private MainPageApi userServiceApi;


    private static final int MAX_COUNT_TIME = 60;

    private Observable mObservableCountTime;

    private Observer<Long> mConsumerCountTime;

    private Disposable mDisposable;

    @Inject
    public Environment mEnv;

    @Inject
    public Retrofit mRetrofit;

    @Inject
    public MainPresenter() {
        super();
    }

    @Override
    public void onAttach(Contract.View rootView) {
        super.onAttach(rootView);

        userServiceApi = mRetrofit.create(MainPageApi.class);


        // 进入登录页面后，删除保存的所有sharedpreference信息
        mEnv.appPreferencesHelper().removeAll();


        mObservableCountTime = Observable.interval(1, TimeUnit.SECONDS, Schedulers.io()).take(MAX_COUNT_TIME) //将递增数字替换成递减的倒计时数字
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return MAX_COUNT_TIME - (aLong + 1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public void getList() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pageSize", Integer.MAX_VALUE);
        jsonObject.addProperty("pageIndex", 1);
        RxResultHelper.getHttpObservable(mRootView.getContext(), userServiceApi.getall(jsonObject))
                .subscribe(new RxResultSubscriber<List<HousingEstate>>() {
                    @Override
                    public void start() {
                        mRootView.showLoading(true);
                    }

                    @Override
                    public void error(String code, String msg) {

                    }

                    @Override
                    public void success(BaseBean<List<HousingEstate>> t) {
                        mRootView.hideLoading();
                        mRootView.showList(t);
                    }
                });
    }

    public void startTimer() {
        mObservableCountTime.subscribe(mConsumerCountTime);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
