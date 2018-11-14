package com.cygrove.libcore.register.moudule;

import com.cygrove.libcore.register.RegisterActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public abstract class RegisterModule {
    @Provides
    @Named("activityName")
    static String provideActivityName() {
        return RegisterActivity.class.getSimpleName();
    }
}