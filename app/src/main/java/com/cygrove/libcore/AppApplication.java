package com.cygrove.libcore;


import com.cygrove.libcore.di.component.ApplicationComponent;
import com.cygrove.libcore.di.component.DaggerApplicationComponent;
import com.cygrove.libcore.network.GlobalHttpHandlerImpl;
import com.cygrove.libcore.di.module.ApplicationModule;
import com.cygrove.libcore.di.module.GlobalConfigModule;


/**
 *
 */
public class AppApplication extends BaseApplication {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void initDaggerComponent() {
        GlobalConfigModule.Builder configModuleBuilder = new GlobalConfigModule.Builder()
                .globalHttpHandler(new GlobalHttpHandlerImpl())
                .baseUrl("https://t-xcc.xsftest.com");
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .globalConfigModule(new GlobalConfigModule(configModuleBuilder))
                .build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}