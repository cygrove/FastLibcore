package com.cygrove.libcore.qrcode.mvp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ScanModule {
    @Provides
    @Named("activityName")
    static String ProvideActivityName() {
        return ScanActivity.class.getSimpleName();
    }
}