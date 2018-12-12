package com.cygrove.libcore.home.mvp;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.cygrove.libcore.base.BaseFragment;
import com.cygrove.libcore.dialog.MessageDialogBuilder;
import com.cygrove.widget.PickerHelper;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class HomepageFragment extends BaseFragment implements Contract.View {
    @BindView(R.id.text)
    TextView text;
    private String s;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        text.setText(getArguments().getString("tab"));
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        Logger.e("第" + getArguments().getString("tab") + "个显示了");
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
                        .setCancelable(true)
                        .setNegativeButton("取消", (dialogInterface, i) -> {
                        })
                        .setPositiveButton("确定", (dialogInterface, i) -> {

                        }).create();
                dialog.setTitle("title");
                dialog.setMessage("message");
                dialog.show();
                break;
            case "2":
                new MessageDialogBuilder(mContext)
                        .setOk("确定", dialog1 -> {
                            showToast("hello");
                        })
                        .setTitle("title")
                        .setCancel("cancel", Dialog::cancel)
                        .setMessage("message").build().show();
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