package com.cygrove.libcore.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.cygrove.libcore.di.qualifiers.AppVersion;
import com.cygrove.libcore.di.qualifiers.ApplicationContext;
import com.cygrove.libcore.di.qualifiers.NetworkType;
import com.cygrove.libcore.di.qualifiers.PackageName;
import com.cygrove.libcore.di.qualifiers.PreferenceInfo;
import com.cygrove.libcore.env.Environment;
import com.cygrove.libcore.utils.AppPreferencesHelper;
import com.cygrove.libcore.utils.AppUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author xiongms
 * @time 2018-08-16 10:28
 */
@Module
public class ApplicationModule {

    private static final String TAG = ApplicationModule.class.getSimpleName();

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Environment provideEnvironment(
            final @NonNull Gson gson,
            final AppPreferencesHelper appPreferencesHelper,
            final Retrofit rqbRetrofit,
            final @NetworkType int networkOperator,
            final @PackageName String packageName,
            final @AppVersion String appVersion) {
        return Environment.builder()
                .networkOperator(networkOperator)
                .packageName(packageName)
                .appVersion(appVersion)
                .rqbRetrofit(rqbRetrofit)
                .appPreferencesHelper(appPreferencesHelper)
                .build();
    }

    @Singleton
    @Provides
    @ApplicationContext
    Context providesContext() {
        return mApplication.getApplicationContext();
    }


    @Provides
    @PreferenceInfo
    @Singleton
    String providePreferenceName(@ApplicationContext Context context) {
        return context.getPackageName() + "_preferences";
    }

    @Provides
    @Singleton
    @PackageName
    String providePackageName(@ApplicationContext Context context) {
        return context.getPackageName();
    }

    @Provides
    @Singleton
    @AppVersion
    String provideAppVersionName(@ApplicationContext Context context) {
        return AppUtil.getAppVersionName(context);
    }

    @Provides
    @Singleton
    @NetworkType
    int provideNetworkType(@ApplicationContext Context context) {
        return AppUtil.getOperator(context);
    }

}
