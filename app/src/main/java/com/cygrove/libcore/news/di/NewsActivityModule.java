package com.cygrove.libcore.news.di;

import com.cygrove.libcore.news.mvp.NewsActivity;
import com.cygrove.libcore.news.mvp.NewsModule;
import com.cygrove.libcore.di.component.BaseActivityComponent;
import com.cygrove.libcore.di.module.DefaultActivityModule;
import com.cygrove.libcore.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class NewsActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = {DefaultActivityModule.class, NewsModule.class})
    abstract NewsActivity contributyActivity();
}
