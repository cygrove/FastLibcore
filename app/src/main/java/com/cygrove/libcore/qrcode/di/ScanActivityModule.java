package com.cygrove.libcore.qrcode.di;

import com.cygrove.libcore.qrcode.mvp.ScanActivity;
import com.cygrove.libcore.qrcode.mvp.ScanModule;
import com.cygrove.libcore.di.component.BaseActivityComponent;
import com.cygrove.libcore.di.module.DefaultActivityModule;
import com.cygrove.libcore.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class ScanActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = {DefaultActivityModule.class, ScanModule.class})
    abstract ScanActivity contributeActivity();
}