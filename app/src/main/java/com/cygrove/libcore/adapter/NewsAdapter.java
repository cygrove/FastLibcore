package com.cygrove.libcore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cygrove.libcore.R;
import com.cygrove.libcore.news.bean.NewsEntry;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context mContext;
    private List<NewsEntry> items = new ArrayList<>();

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<NewsEntry> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NewsEntry entry = items.get(i);
        if (!entry.isReadable()) {//未读
            viewHolder.mTitleTv.setTextColor(0xff333333);
            viewHolder.mContentTv.setTextColor(0xff666666);
            viewHolder.mTimeTv.setTextColor(0xff666666);
            viewHolder.mUnreadView.setVisibility(View.VISIBLE);
        } else {//已读
            viewHolder.mTitleTv.setTextColor(0xff666666);
            viewHolder.mContentTv.setTextColor(0xff999999);
            viewHolder.mTimeTv.setTextColor(0xff999999);
            viewHolder.mUnreadView.setVisibility(View.GONE);
        }
        viewHolder.mTitleTv.setText(entry.getTitle());
        viewHolder.mContentTv.setText(entry.getMsg());
        viewHolder.mTimeTv.setText(entry.getTimestamp() + "");
        viewHolder.layout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
            @Override
            public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
            }

            @Override
            public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {

            }

            @Override
            public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
                if (viewHolder.layout.isOpened())
                    viewHolder.layout.closeWithAnim();
            }
        });
        viewHolder.mDeleteTv.setOnClickListener(v -> {
            items.remove(i);
            notifyItemRemoved(viewHolder.getLayoutPosition());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView mDeleteTv;
        View mUnreadView;
        TextView mTitleTv;
        TextView mTimeTv;
        TextView mContentTv;
        LinearLayout mItemView;
        BGASwipeItemLayout layout;

        ViewHolder(View view) {
            super(view);
            this.mDeleteTv = (TextView) view.findViewById(R.id.delete_tv);
            this.mUnreadView = (View) view.findViewById(R.id.unread_view);
            this.mTitleTv = (TextView) view.findViewById(R.id.title_tv);
            this.mTimeTv = (TextView) view.findViewById(R.id.time_tv);
            this.mContentTv = (TextView) view.findViewById(R.id.content_tv);
            this.mItemView = (LinearLayout) view.findViewById(R.id.item_view);
            layout = (BGASwipeItemLayout) itemView;
        }
    }
}