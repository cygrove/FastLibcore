package com.cygrove.libcore.register;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cygrove.libcore.R;
import com.cygrove.libcore.news.mvp.NewsActivity;
import com.cygrove.libcore.register.contract.Contract;
import com.cygrove.libcore.register.persenter.RegisterPersenter;
import com.xiongms.libcore.config.RouterConfig;
import com.xiongms.libcore.mvp.BaseActivity;
import com.xiongms.libcore.utils.ActivityUtil;
import com.xiongms.libcore.utils.AppPreferencesHelper;
import com.xiongms.libcore.utils.LoadViewHelper;
import com.xiongms.libcore.utils.ResourcesUtil;
import com.xiongms.statusbar.StatusBarHelper;
import com.xiongms.widget.TitleView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

@Route(path = RouterConfig.ROUTER_LOGIN)
public class RegisterActivity extends BaseActivity<RegisterPersenter> implements Contract.View {
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.ll_rootview)
    LinearLayout llRootview;
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.btn_cheak_new_version)
    Button btnCheakNewVersion;
    @BindView(R.id.btn_get_token)
    Button btnGetToken;
    @BindView(R.id.btn_jump)
    Button btnJump;
    @BindView(R.id.btn_clear_token)
    Button btnClearToken;
    @Inject
    public AppPreferencesHelper spHelper;

    @Override

    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        boolean isTokenError = getIntent().getBooleanExtra("TokenError", false);
        if (isTokenError) {
            ActivityUtil.getInstance().clearAllActivityWithout(RegisterActivity.class);
        }
        mPresenter.onAttach(this);
        titleView.setTitle("Demo");
        titleView.setMenuImgIcon(R.drawable.ic_plus);
        titleView.setMenuImgClickListener(view -> showToast("onclickMenu"));
        StatusBarHelper.setStatusBarColor(this, ResourcesUtil.getColor(R.color.text_green));
        requestPermissions();
    }

    @Override
    public String getPhone() {
        return edPhone.getText().toString().trim();
    }

    @Override
    public void showEmptyView() {
        loadViewHelper = new LoadViewHelper(llRootview);
        loadViewHelper.showEmpty();
        loadViewHelper.setOnRefreshListener(() -> mPresenter.clickRefrsh());
    }

    @Override
    public void showNomalView() {
        loadViewHelper.showContent();
    }

    @Override
    public void jump() {
        push(NewsActivity.class);
    }


    @OnClick({R.id.btn_send, R.id.btn_next, R.id.btn_cheak_new_version, R.id.btn_get_token, R.id.btn_jump, R.id.btn_clear_token})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                mPresenter.getCode();
                break;
            case R.id.btn_next:
                mPresenter.clickShowEmpty();
                break;
            case R.id.btn_cheak_new_version:
                mPresenter.cheakNewVersion();
                break;
            case R.id.btn_get_token:
                mPresenter.getToken();
                break;
            case R.id.btn_jump:
                jump();
                break;
            case R.id.btn_clear_token:
                spHelper.setToken("123");
                break;
        }
    }

    private void requestPermissions() {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CHANGE_WIFI_STATE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "为保证APP正常使用，请允许存储、相机等权限", 101, perms);
        }
    }
}