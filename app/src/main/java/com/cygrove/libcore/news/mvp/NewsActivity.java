package com.cygrove.libcore.news.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.gifdecoder.GifHeader;
import com.cygrove.libcore.R;
import com.cygrove.libcore.adapter.NewsAdapter;
import com.cygrove.libcore.news.bean.NewsEntry;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xiongms.libcore.mvp.BaseMVPActivity;
import com.xiongms.libcore.utils.LoadViewHelper;
import com.xiongms.widget.TitleView;

import java.util.List;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

public class NewsActivity extends BaseMVPActivity<NewPersenter> implements Contract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.title)
    TitleView title;
    private NewsAdapter adapter;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_news;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        title.setTitle("列表");
        refreshLayout.setHeaderInsetStart(DensityUtil.px2dp(title.getHeight()));
        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.refreshData());
        refreshLayout.setOnLoadMoreListener(refreshLayout -> mPresenter.loadmoreData());
        loadViewHelper = new LoadViewHelper(refreshLayout);
        mPresenter.reqData(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(this);
        recyclerView.setAdapter(adapter);
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
    public void showLoading(boolean isDialog) {
        loadViewHelper.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        loadViewHelper.showContent();
    }
}