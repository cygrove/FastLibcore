package com.cygrove.libcore.main.di.module;

import com.cygrove.libcore.main.MainActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public abstract class MainModule {

    @Provides
    @Named("activityName")
    static String provideActivityName() {
        return MainActivity.class.getSimpleName();
    }
}

