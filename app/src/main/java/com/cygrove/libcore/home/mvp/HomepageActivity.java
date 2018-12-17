package com.cygrove.libcore.home.mvp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cygrove.libcore.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.cygrove.libcore.mvp.BaseMVPActivity;
import com.cygrove.widget.NoScrollViewPager;
import com.cygrove.widget.QRBQMUITabSegment;
import com.cygrove.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import statusbar.cygrove.com.statusbarhelper.StatusBarHelper;

public class HomepageActivity extends BaseMVPActivity<HomepagePersenter> implements Contract.View {
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.tabsegment)
    QRBQMUITabSegment tabsegment;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    private List<Fragment> fragmentList;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_homepage;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleView.setTitle("首页？");
        tabsegment.setMode(QRBQMUITabSegment.MODE_FIXED);
        tabsegment.setIndicatorColor(Color.parseColor("#d7a671"));
        tabsegment.setHasIndicator(true);
        tabsegment.setIndicatorPosition(false);
        tabsegment.setIndicatorWidthAdjustContent(true);
        tabsegment.addTab(getTabSegment("第1"));
        tabsegment.addTab(getTabSegment("第2"));
        tabsegment.addTab(getTabSegment("第3"));
        tabsegment.addTab(getTabSegment("第4"));

        tabsegment.setMinTabTextSize(QMUIDisplayHelper.sp2px(getContext(), 16));
        tabsegment.setMaxTabTextSize(QMUIDisplayHelper.sp2px(getContext(), 17));
        tabsegment.setDefaultNormalColor(Color.parseColor("#c5b6a6"));
        tabsegment.setDefaultSelectedColor(getResources().getColor(R.color.text_black));
        tabsegment.selectTab(0);
        tabsegment.notifyDataChanged();

        tabsegment.addOnTabSelectedListener(new QRBQMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                tabChanged(index);
            }

            @Override
            public void onTabUnselected(int index) {
            }

            @Override
            public void onTabReselected(int index) {
                tabChanged(index);
            }

            @Override
            public void onDoubleTap(int index) {
            }
        });
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomepageFragment());
        fragmentList.add(new HomepageFragment());
        fragmentList.add(new HomepageFragment());
        fragmentList.add(new HomepageFragment());
        iViewpagerAdapter viewpagerAdapter = new iViewpagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(viewpagerAdapter);
        viewpager.setOffscreenPageLimit(4);
    }

    private void tabChanged(int index) {
        mPresenter.tabChanage(index);
        viewpager.setCurrentItem(index);
        throw new RuntimeException("changedException");
    }

    public void setStatus(int color) {
        StatusBarHelper.setStatusBarColor(this, color, false);
    }

    private QRBQMUITabSegment.Tab getTabSegment(String title) {
        QRBQMUITabSegment.Tab tab = new QRBQMUITabSegment.Tab(title);
        return tab;
    }


    class iViewpagerAdapter extends FragmentPagerAdapter {
        public iViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            bundle.putString("tab", i + "");
            fragmentList.get(i).setArguments(bundle);
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}