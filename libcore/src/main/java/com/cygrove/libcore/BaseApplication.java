package com.cygrove.libcore;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cygrove.libcore.utils.AppUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.cygrove.libcore.config.AppConfig;
import com.cygrove.libcore.env.Environment;
import com.cygrove.libcore.glide.GlideLoader;
import com.cygrove.libcore.utils.AppPreferencesHelper;
import com.cygrove.libcore.utils.FileUtil;
import com.cygrove.libcore.utils.LoadViewHelper;
import com.cygrove.libcore.utils.ResourcesUtil;
import com.cygrove.libcore.utils.ToastUtil;
import com.cygrove.widget.GifHeader;
import com.singhajit.sherlock.core.Sherlock;
import com.singhajit.sherlock.core.investigation.AppInfo;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * @author cygrove
 * @time 2018-11-02 10:38
 */
public abstract class BaseApplication extends Application implements HasActivityInjector {

    private static BaseApplication mApplication;

    public boolean mIsForeground = false;

    public boolean mIsBackToForeground = false;

    @Inject
    public Environment mEnv;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    public static BaseApplication getInstance() {
        return mApplication;
    }

    /**
     * 添加多dex包支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initDaggerComponent();
        init();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    public void init() {
        /**
         * 注意各个组件初始化顺序
         */
        initLogger();
        ResourcesUtil.init(getResources());
        // 部分机型中兼容vector图片
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initArouter();
        initRefreshLayout();
        initLoadingHelper();
        initGlideConfig();
        registerActivityLifecycleCallbacks();
        FileUtil.createDefaultDir();
        ToastUtil.init(this);
        initSherlock();
    }

    private void initSherlock() {
        Sherlock.init(this); //Initializing Sherlock
        Sherlock.setAppInfoProvider(() -> new AppInfo.Builder()
                .with("Version", AppUtil.getAppVersionName(this)) //You can get the actual version using "AppInfoUtil.getAppVersion(context)"
                .build());
    }

    public Environment getEnv() {
        return mEnv;
    }

    public AppPreferencesHelper getPreferences() {
        return getInstance().getEnv().appPreferencesHelper();
    }

    public Context getContext() {
        return mApplication.getApplicationContext();
    }

    private void initRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColors(Color.parseColor("#444444"), Color.WHITE);//全局设置主题颜色
            return new GifHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(10f).setFinishDuration(150);
        });
    }

    private void initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(mApplication);
    }

    /**
     * 初始化加载界面，空界面等
     */
    private void initLoadingHelper() {
        LoadViewHelper.getBuilder()
                .setLoadDefault(R.layout.default_view)
                .setLoadEmpty(R.layout.empty_view)
                .setLoadError(R.layout.error_view)
                .setLoadIng(R.layout.loading_view);
    }

    /**
     * 初始化一些Gilde的配置
     */
    private void initGlideConfig() {
        GlideLoader.getBuilder()
                .setDefault(R.mipmap.lib_ic_launcher)
                .setDefaultCircle(R.mipmap.lib_ic_launcher)
                .setDefaultHead(R.mipmap.lib_ic_launcher)
                .setDefaultOther(R.mipmap.lib_ic_launcher);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(AppConfig.LOGGER_TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private void registerActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            private int refCount = 0;

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

                if (refCount == 0) {
                    mIsBackToForeground = true;
                } else {
                    mIsBackToForeground = false;
                }
                refCount++;
                mIsForeground = true;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                refCount--;
                if (refCount == 0) {
                    mIsForeground = false;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    public abstract void initDaggerComponent();
}