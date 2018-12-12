package com.cygrove.libcore.di.component;

import com.cygrove.libcore.base.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 *
 */
@Subcomponent(modules = {AndroidInjectionModule.class,})
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {
    //每一个继承BaseActivity的Activity，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {
    }
}
