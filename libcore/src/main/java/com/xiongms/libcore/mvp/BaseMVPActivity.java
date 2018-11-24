package com.xiongms.libcore.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiongms.libcore.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Activity的基类
 *
 * @author cygrove
 * @time 2018-11-12 11:31
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends BaseActivity {

    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        mPresenter.onAttach(this);
        super.onCreate(savedInstanceState);
    }

    /**
     * 返回为true时，父类不调用AndroidInjection.inject
     *
     * @return
     */
    @Override
    protected boolean isMVPMode() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetach();//释放资源
        mPresenter = null;
    }
}