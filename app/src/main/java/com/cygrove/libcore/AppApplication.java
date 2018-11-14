package com.cygrove.libcore;


import com.cygrove.libcore.di.component.ApplicationComponent;
import com.cygrove.libcore.di.component.DaggerApplicationComponent;
import com.cygrove.libcore.network.GlobalHttpHandlerImpl;
import com.xiongms.libcore.BaseApplication;
import com.xiongms.libcore.di.module.ApplicationModule;
import com.xiongms.libcore.di.module.GlobalConfigModule;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> baseUrls = new HashMap<>();
        baseUrls.put("DOMAIN", "https://ywf.xsfapp.com");
        GlobalConfigModule.Builder configModuleBuilder = new GlobalConfigModule.Builder()
                .globalHttpHandler(new GlobalHttpHandlerImpl())
                .baseUrl("https://ywf.xsfapp1.com")
                .baseUrls(baseUrls);
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