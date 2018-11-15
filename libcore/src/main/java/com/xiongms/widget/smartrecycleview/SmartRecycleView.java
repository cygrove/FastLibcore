package com.xiongms.widget.smartrecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiongms.libcore.R;


/**
 * @author cygrove
 * @time 2018-11-15 18:01
 */
public class SmartRecycleView extends FrameLayout implements OnRefreshListener, OnLoadMoreListener {
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private SmartRecycleViewAdapter adapter;
    private int currentPage = 1;

    public SmartRecycleView(Context context) {
        this(context, null);
    }

    public SmartRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_smart_recycleview, this, true);
        recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    public SmartRecycleViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(SmartRecycleViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
    }
}