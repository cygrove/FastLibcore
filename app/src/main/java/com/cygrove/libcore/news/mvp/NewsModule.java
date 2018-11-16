package com.cygrove.libcore.news.mvp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class NewsModule {
    @Provides
    @Named("activityName")
    static String provideActivityName() {
        return NewsActivity.class.getSimpleName();
    }
}