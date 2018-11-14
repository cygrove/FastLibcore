package com.cygrove.libcore.main.di.module;

import com.cygrove.libcore.main.MainActivity;
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
public abstract class MainActivitysModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {DefaultActivityModule.class, MainModule.class})
    abstract MainActivity contributeActivityInjector();
}