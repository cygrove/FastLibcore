package com.cygrove.libcore.di.component;

import com.cygrove.libcore.AppApplication;
import com.cygrove.libcore.main.di.module.MainActivitysModule;
import com.cygrove.libcore.register.moudule.RegisterActivityModule;
import com.xiongms.libcore.di.module.ApplicationModule;
import com.xiongms.libcore.di.module.GlobalConfigModule;
import com.xiongms.libcore.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author xiongms
 * @time 2018-11-07 16:12
 */
@Singleton
@Component(modules = {
        // dagger2.android相关Module
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        // 公共的Module
        GlobalConfigModule.class,
        ApplicationModule.class,
        NetModule.class,
        MainActivitysModule.class,
        RegisterActivityModule.class})

public interface ApplicationComponent {
    void inject(AppApplication application);
}