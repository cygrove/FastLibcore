package com.cygrove.libcore.news.mvp;

import com.cygrove.libcore.news.bean.NewsEntry;
import com.xiongms.libcore.mvp.IPresenter;
import com.xiongms.libcore.mvp.IView;

import java.util.List;

public interface Contract {
    interface View extends IView {
        void setView(List<NewsEntry> items);

        void setEnableLoadMore(boolean b);

        void showEmptyView();
    }

    interface Persenter extends IPresenter<View> {
        void reqData();

        void refreshData();

        void loadmoreData();
    }
}