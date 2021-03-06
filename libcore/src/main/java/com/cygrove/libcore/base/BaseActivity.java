package com.cygrove.libcore.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.cygrove.libcore.mvp.IView;
import com.cygrove.libcore.utils.ActivityUtil;
import com.cygrove.libcore.utils.LoadViewHelper;
import com.cygrove.libcore.utils.LoadingDialogUtil;
import com.cygrove.libcore.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity的基类
 *
 * @author cygrove
 * @time 2018-11-12 11:31
 */
public abstract class BaseActivity extends RxAppCompatActivity implements IView {
    public LoadViewHelper loadViewHelper;
    protected Context mContext;
    private LoadingDialogUtil mLoadingDialogUtil;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityUtil.getInstance().addActivity(this);
        mLoadingDialogUtil = new LoadingDialogUtil();
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }


    /**
     * 初始化 布局资源文件ID, 如果 {@link #initView(Bundle)} 返回 0, 框架则不会调用 {@link Activity#setContentView(int)}
     *
     * @param savedInstanceState
     * @return
     */
    public abstract int initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    public abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onDestroy() {
        ActivityUtil.getInstance().removeActivity(this);
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;

        if (mLoadingDialogUtil != null) {
            mLoadingDialogUtil.destoryLoadingDialog();
            mLoadingDialogUtil = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void showLoadingDialog() {
        if (mLoadingDialogUtil != null) {
            mLoadingDialogUtil.showLoadingDialog(this);
        }
    }

    public void cancelLoadingDialog() {
        if (mLoadingDialogUtil != null) {
            mLoadingDialogUtil.cancelLoadingDialog();
        }
    }

    public void setLoaddingDialogText(String text) {
        if (mLoadingDialogUtil != null) {
            mLoadingDialogUtil.setLoadingText(text);
        }
    }

    public void showLoading(boolean isDialog) {
        if (isDialog) {
            showLoadingDialog();
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.show(msg);
    }

    public void hideLoading() {
        cancelLoadingDialog();
    }

    /**
     * 跳转
     *
     * @param to
     */
    public void push(Class<? extends BaseActivity> to) {
        Intent intent = new Intent(getContext(), to);
        startActivity(intent);
    }

    public void pushExtra(Class<? extends BaseActivity> to, Intent intent) {
        intent.setClass(getContext(), to);
        startActivity(intent);
    }

    public void pushExtraForResult(Class<? extends BaseActivity> to, Intent intent, int requestCode) {
        intent.setClass(getContext(), to);
        startActivityForResult(intent, requestCode);
    }
}