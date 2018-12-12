package com.cygrove.libcore.register.persenter;

import android.support.v4.app.FragmentActivity;

import com.cygrove.libcore.R;
import com.cygrove.libcore.register.VersionInfo;
import com.cygrove.libcore.register.api.CheckNewVersion;
import com.cygrove.libcore.register.api.LoginApi;
import com.cygrove.libcore.register.api.RegisterApi;
import com.cygrove.libcore.register.contract.Contract;
import com.cygrove.libcore.register.moudule.LoginMoudule;
import com.google.gson.JsonObject;
import com.cygrove.libcore.bean.BaseBean;
import com.cygrove.libcore.mvp.BasePresenter;
import com.cygrove.libcore.network.rx.RxResultHelper;
import com.cygrove.libcore.network.rx.RxResultSubscriber;
import com.cygrove.libcore.secret.SecretUtil;
import com.cygrove.libcore.updateversion.CheckUpdateOption;
import com.cygrove.libcore.updateversion.Q;
import com.cygrove.libcore.utils.AppPreferencesHelper;
import com.cygrove.libcore.utils.FileUtil;
import com.cygrove.libcore.utils.ResourcesUtil;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class RegisterPersenter extends BasePresenter<Contract.View> implements Contract.Persenter {
    private RegisterApi api;
    private CheckNewVersion versionApi;
    private LoginApi loginApi;
    @Inject
    public Retrofit retrofit;
    @Inject
    public AppPreferencesHelper spHelper;

    @Inject
    public RegisterPersenter(Retrofit retrofit) {
        super();
        api = retrofit.create(RegisterApi.class);
        versionApi = retrofit.create(CheckNewVersion.class);
        loginApi = retrofit.create(LoginApi.class);
    }

    @Override
    public void onAttach(Contract.View rootView) {
        super.onAttach(rootView);
    }


    @Override
    public void clickShowEmpty() {
        mRootView.showEmptyView();
    }

    @Override
    public void clickRefrsh() {
        mRootView.showNomalView();
    }

    @Override
    public void cheakNewVersion() {
        RxResultHelper.getHttpObservable(mRootView.getContext(), versionApi.checkVersion()).subscribe(new RxResultSubscriber<VersionInfo>() {
            @Override
            public void start() {
                mRootView.showLoading(true);
            }

            @Override
            public void error(String code, String msg) {
                mRootView.showToast(msg);
                mRootView.hideLoading();
            }

            @Override
            public void success(BaseBean<VersionInfo> t) {
                mRootView.hideLoading();
                t.getBody().setUrl("http://shouji.360tpcdn.com/58LIE0lufdThR-tKwaT1RFJnYzYVGpmPcfD6J3dagwJGkSIbUtTHHn0ALX5HG9kySsaAkt-9u9LPgEh2ttViaCo7IpYyF0zgJzKUZwcBw1k1.apk?sign=f6966f2fc04ca843");
                CheckUpdateOption option = new CheckUpdateOption.Builder()
                        .setAppName(ResourcesUtil.getString(R.string.app_name))
                        .setFileName("xcc_apk")
                        .setFilePath(FileUtil.getUploadCacheDir())
                        .setImageUrl("http://pic19.nipic.com/20120308/4970979_102637717125_2.jpg")
                        .setIsForceUpdate(false)
                        .setNewAppSize(14)
                        .setNewAppUpdateDesc(t.getBody().getUpContent())
                        .setNewAppUrl(t.getBody().getUrl())
                        .setNewAppVersionName(t.getBody().getVersion())
                        .setNotificationSuccessContent("下载成功，点击安装")
                        .setNotificationFailureContent("下载失败，点击重新下载")
                        .setNotificationIconResId(R.mipmap.ic_launcher)
                        .setNotificationTitle(t.getBody().getTitle())
                        .build();
                Q.show((FragmentActivity) mRootView.getContext(), option, true);
            }
        });
    }

    @Override
    public void getToken() {
        JsonObject json = new JsonObject();
        json.addProperty("pushId", "1507bfd3f790d00c477");
        json.addProperty("pwd", SecretUtil.getDesSecret("111111", "bx#x@iwH"));
        json.addProperty("account", "15123130833");
        RxResultHelper.getHttpObservable(mRootView.getContext(), loginApi.getToken(json)).subscribe(new RxResultSubscriber<LoginMoudule>() {
            @Override
            public void start() {
                mRootView.showLoading(true);
            }

            @Override
            public void error(String code, String msg) {
                mRootView.hideLoading();
                mRootView.showToast(msg);
            }

            @Override
            public void success(BaseBean<LoginMoudule> t) {
                mRootView.hideLoading();
                spHelper.setToken(t.getBody().token);
                mRootView.saveEntry(t.getBody());
                mRootView.showToast("已经存入token:" + spHelper.getToken());
            }
        });
    }

    @Override
    public void clickClearToken() {
        spHelper.setToken("111");
    }
}