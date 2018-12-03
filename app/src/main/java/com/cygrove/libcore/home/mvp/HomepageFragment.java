package com.cygrove.libcore.home.mvp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cygrove.libcore.R;
import com.cygrove.libcore.qrcode.mvp.ScanActivity;
import com.xiongms.libcore.base.BaseFragment;
import com.xiongms.widget.PickerHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class HomepageFragment extends BaseFragment implements Contract.View {
    @BindView(R.id.text)
    TextView text;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        String s = getArguments().getString("tab");
        HomepageActivity activity = (HomepageActivity) mActivity;
        switch (s) {
            case "0":
                activity.setStatus(Color.RED);
                break;
            case "1":
                activity.setStatus(Color.GREEN);
                break;
            case "2":
                activity.setStatus(Color.YELLOW);
                break;
            case "3":
                activity.setStatus(Color.BLUE);
                break;
        }
        text.setText(s);
    }


    @OnClick(R.id.text)
    public void onViewClicked() {
        String s = getArguments().getString("tab");
        switch (s) {
            case "0":
                HomepageActivity activity = (HomepageActivity) mActivity;
                activity.push(ScanActivity.class);
                break;
            case "1":
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setNegativeButton("取消", (dialogInterface, i) -> {

                        }).setPositiveButton("确定", (dialogInterface, i) -> {

                        }).create();
                dialog.setTitle("title");
                dialog.setMessage("message");
                dialog.show();
                break;
            case "2":
                break;
            case "3":
                PickerHelper helper = new PickerHelper();
                String weeks[] = new String[]{"2017", "2018"};
                helper.showWeekPicker(mActivity, weeks, "周周", true, new PickerHelper.OnSelectListener() {
                    @Override
                    public void onSelect(String options1, String options2, String options3) {
                        showToast(options1 + options2 + options3);
                    }
                });
                break;
        }

    }
}