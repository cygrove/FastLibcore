package com.cygrove.libcore.home.di;

import com.cygrove.libcore.home.mvp.HomepageActivity;
import com.cygrove.libcore.home.mvp.HomepageModule;
import com.cygrove.libcore.di.component.BaseActivityComponent;
import com.cygrove.libcore.di.module.DefaultActivityModule;
import com.cygrove.libcore.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class HomepageActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = {DefaultActivityModule.class, HomepageModule.class})
    abstract HomepageActivity contributsActivityModule();
}