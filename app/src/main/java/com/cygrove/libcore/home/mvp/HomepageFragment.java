package com.cygrove.libcore.home.mvp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cygrove.libcore.R;
import com.xiongms.libcore.base.BaseFragment;
import com.xiongms.widget.datetimepicker.DateTimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        Date endDate = new Date();
        Calendar c = new GregorianCalendar();
        c.add(Calendar.YEAR, -1);

        DateTimePicker dateTimePicker = new DateTimePicker.Builder(getContext())
                .setCancelTextColor(getResources().getColor(R.color.text_black))
                .setOkTextColor(getResources().getColor(R.color.text_gold))
                .setTitleTextColor(getResources().getColor(R.color.text_gray))
                .setTextColor(getResources().getColor(R.color.text_gray))
                .setSelectedTextColor(getResources().getColor(R.color.text_black))
                .setDividerColor(getResources().getColor(R.color.line_color))
                .setDividerWidth(2f)
                .setKeepLastSelected(true)
                .setShowYMDHMLabel(false)
                .setTextSize(getResources().getDimensionPixelSize(R.dimen.text_30px),
                        getResources().getDimensionPixelSize(R.dimen.text_30px))
                .setShowType(DateTimePicker.ShowType.WEEK)
                .setStartDate(c.getTime())
                .setEndDate(endDate)
                .build();
        dateTimePicker.setResultHandler(date -> {
        });

        dateTimePicker.show(c.getTime());
    }
}