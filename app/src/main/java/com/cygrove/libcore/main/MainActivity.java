package com.cygrove.libcore.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cygrove.libcore.R;
import com.cygrove.libcore.main.contract.Contract;
import com.cygrove.libcore.main.entity.HousingEstate;
import com.cygrove.libcore.main.persenter.MainPresenter;
import com.xiongms.libcore.bean.BaseBean;
import com.xiongms.libcore.base.BaseActivity;
import com.xiongms.libcore.mvp.BaseMVPActivity;
import com.xiongms.widget.TitleView;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseMVPActivity<MainPresenter> implements Contract.View {
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.iv_community_bg)
    ImageView ivCommunityBg;
    @BindView(R.id.spinner_comminity)
    Spinner spinnerComminity;
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String[] dataArrays;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.onAttach(this);
        mPresenter.getList();
    }

    @Override
    public String getPhone() {
        return "123";
    }

    @Override
    public String getCode() {
        return "321";
    }

    @Override
    public void showList(BaseBean<List<HousingEstate>> t) {
        dataArrays = new String[t.getBody().size()];
        for (int i = 0; i < t.getBody().size(); i++) {
            dataArrays[i] = t.getBody().get(i).getCommunityName();
        }
        spinnerComminity.setAdapter(new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, dataArrays));
        spinnerComminity.setSelection(0, false);
    }
}