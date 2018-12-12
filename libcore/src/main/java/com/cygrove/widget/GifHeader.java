package com.cygrove.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.cygrove.libcore.R;
import com.cygrove.libcore.glide.GlideApp;
import com.cygrove.libcore.utils.ResourcesUtil;

/**
 * @author : cygrove
 * @date : 2018-12-07 15:23
 */
public class GifHeader extends LinearLayout implements RefreshHeader {
    private TextView mHeaderText;//标题文本
    private ImageView iv_refresh;
    private int gifResourceId = R.drawable.gif_refresh;

    public GifHeader(Context context) {
        super(context);
        initView(context);
    }

    public GifHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public GifHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        mHeaderText = new TextView(context);
        mHeaderText.setTextColor(ResourcesUtil.getColor(R.color.text_gray));
        iv_refresh = new ImageView(context);
        iv_refresh.setImageResource(gifResourceId);
        addView(iv_refresh, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(new View(context), DensityUtil.dp2px(10), DensityUtil.dp2px(20));
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Scale;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
        GlideApp.with(getContext()).load(gifResourceId).diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(iv_refresh);
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        iv_refresh.setImageResource(gifResourceId);
        if (success) {
            mHeaderText.setText("刷新完成");
        } else {
            mHeaderText.setText("刷新失败");
        }
        return 250;//延迟250毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mHeaderText.setText("下拉刷新");
                iv_refresh.setImageResource(gifResourceId);
                break;
            case Refreshing:
                mHeaderText.setText("正在刷新");
                GlideApp.with(getContext()).load(gifResourceId).diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(iv_refresh);
                break;
            case ReleaseToRefresh:
                mHeaderText.setText("释放刷新");
                iv_refresh.setImageResource(gifResourceId);
                break;
        }
    }

    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        SmartRefreshLayout.LayoutParams lp = (SmartRefreshLayout.LayoutParams) this.getLayoutParams();
        lp.height = height;
        this.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        SmartRefreshLayout.LayoutParams lp = (SmartRefreshLayout.LayoutParams) this.getLayoutParams();
        return lp.height;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onPulling(float percent, int offset, int headHeight, int maxDragHeight) {
        if (getVisibleHeight() > 0 || offset > 0) {
            setVisibleHeight((int) (offset + getVisibleHeight()));
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headHeight, int maxDragHeight) {
    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }
}