package com.cygrove.libcore.register.moudule;

import com.cygrove.libcore.register.RegisterActivity;
import com.xiongms.libcore.di.component.BaseActivityComponent;
import com.xiongms.libcore.di.module.DefaultActivityModule;
import com.xiongms.libcore.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 *
 */
@Module(subcomponents = {
        BaseActivityComponent.class
})
public abstract class RegisterActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {DefaultActivityModule.class, RegisterModule.class})
    abstract RegisterActivity contributeInjector();
}