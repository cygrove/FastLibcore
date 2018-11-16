package com.cygrove.libcore.news.mvp;

import com.cygrove.libcore.news.api.NewsApi;
import com.cygrove.libcore.news.bean.NewsEntry;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.bean.BasePageBean;
import com.xiongms.libcore.config.AppConfig;
import com.xiongms.libcore.mvp.BasePresenter;
import com.xiongms.libcore.network.rx.RxResultHelper;
import com.xiongms.libcore.network.rx.RxResultSubscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class NewPersenter extends BasePresenter<Contract.View> implements Contract.Persenter {
    private NewsApi api;
    @Inject
    public Retrofit retrofit;
    private int currentPage = 1;
    private List<NewsEntry> items;

    @Inject
    public NewPersenter(Retrofit retrofit) {
        api = retrofit.create(NewsApi.class);
    }

    @Override
    public void onAttach(Contract.View rootView) {
        super.onAttach(rootView);
    }

    @Override
    public void reqData() {
        RxResultHelper.getHttpObservable(mRootView.getContext(), api.reqData(currentPage, AppConfig.PAGE_SIZE))
                .subscribe(new RxResultSubscriber<BasePageBean<NewsEntry>>() {
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
                    public void success(BaseBean<BasePageBean<NewsEntry>> t) {
                        if (items == null) {
                            items = new ArrayList<>();
                        }
                        if (currentPage == 1) {
                            items.clear();
                        }
                        if (t.getBody().getList().size() == 0) {
                            mRootView.showEmptyView();
                        } else {
                            if (t.getBody().getPageNum() < t.getBody().getPageSize()) {
                                mRootView.setEnableLoadMore(true);
                            } else {
                                mRootView.setEnableLoadMore(false);
                            }
                            items.addAll(t.getBody().getList());
                            mRootView.setView(items);
                        }
                        mRootView.hideLoading();
                    }
                });

    }

    @Override
    public void refreshData() {
        currentPage = 1;
        reqData();
    }

    @Override
    public void loadmoreData() {
        currentPage++;
        reqData();
    }
}