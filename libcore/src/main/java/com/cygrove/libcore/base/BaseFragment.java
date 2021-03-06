package com.cygrove.libcore.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.cygrove.libcore.mvp.IView;
import com.cygrove.libcore.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author cygrove
 * @time 2018-11-17 15:55
 */
public abstract class BaseFragment extends RxFragment implements IView {
    protected View mRootView;
    private Unbinder mUnbinder;
    protected Activity mActivity;
    protected Context mContext;
    private boolean mLazyLoad;
    private boolean mIsNeedLazyLoad;

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    public boolean isFragmentVisible;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!hasCreateView || mRootView == null) {
            hasCreateView = true;
            mRootView = initView(inflater, container, savedInstanceState);
            try {
                if (mRootView != null) {
                    //绑定到butterknife
                    mUnbinder = ButterKnife.bind(this, mRootView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            initData(savedInstanceState);
        }
        if (mIsNeedLazyLoad) {
            if (!mLazyLoad) {
                mLazyLoad = true;
                lazyLoad();
            } else {
                lazyLoaded();
            }
        }
        return mRootView;
    }

    /**
     * 初始化 View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    public abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (mRootView == null) {
//            return;
//        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    /**
     * 延迟加载
     */
    public void lazyLoad() {
    }

    /**
     * 延迟加载成功之后再次切换到该页面时会调用该方法
     */
    public void lazyLoaded() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        if (hasCreateView && mRootView != null) {
            if (!mLazyLoad) {
                mLazyLoad = true;
                lazyLoad();
            } else {
                lazyLoaded();
            }
        } else {
            mIsNeedLazyLoad = true;
        }
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading(boolean isDialog) {
        if (isDialog) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).showLoadingDialog();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.show(msg);
    }
}