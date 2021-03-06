package com.cygrove.libcore.news.mvp;

import com.cygrove.libcore.news.bean.NewsEntry;
import com.cygrove.libcore.mvp.IPresenter;
import com.cygrove.libcore.mvp.IView;

import java.util.List;

public interface Contract {
    interface View extends IView {
        void setView(List<NewsEntry> items);

        void setEnableLoadMore(boolean b);

        void showEmptyView();
    }

    interface Persenter extends IPresenter<View> {
        void reqData(boolean isShowLoadding);

        void refreshData();

        void loadmoreData();
    }
}