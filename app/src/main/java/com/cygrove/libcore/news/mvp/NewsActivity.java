package com.cygrove.libcore.news.mvp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.cygrove.libcore.R;
import com.cygrove.libcore.adapter.NewsAdapter;
import com.cygrove.libcore.news.bean.NewsEntry;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.mvp.BaseActivity;
import com.xiongms.libcore.utils.LoadViewHelper;
import com.xiongms.statusbar.StatusBarHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends BaseActivity<NewPersenter> implements Contract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private NewsAdapter adapter;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_news;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarHelper.setStatusBarColor(this, Color.parseColor("#3a000000"), false);

        refreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.refreshData());
        refreshLayout.setOnLoadMoreListener(refreshLayout -> mPresenter.loadmoreData());
        mPresenter.reqData();
        adapter = new NewsAdapter(this);
        loadViewHelper = new LoadViewHelper(refreshLayout);

    }

    @Override
    public void setView(List<NewsEntry> items) {
        adapter.setData(items);
    }

    @Override
    public void setEnableLoadMore(boolean b) {
        refreshLayout.setEnableLoadMore(b);
    }

    @Override
    public void showEmptyView() {
        if (loadViewHelper != null) {
            loadViewHelper.showEmpty();
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        loadViewHelper.showContent();
    }
}