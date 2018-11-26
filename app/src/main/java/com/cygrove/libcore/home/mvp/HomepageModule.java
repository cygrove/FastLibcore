package com.cygrove.libcore.home.mvp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class HomepageModule {
    @Provides
    @Named("activityName")
    static String provideActivityName() {
        return HomepageActivity.class.getSimpleName();
    }
}