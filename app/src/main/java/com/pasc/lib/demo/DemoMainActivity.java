package com.pasc.lib.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.tablayout.PascTab;
import com.pasc.lib.widget.tablayout.PascTabBuilder;
import com.pasc.lib.widget.tablayout.PascTabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class DemoMainActivity extends FragmentActivity {
    private static final int TAB_DESIGNER = 0;
    private static final int TAB_DEVELOPER = 1;

    private ViewPager mViewPager;
    private PascTabLayout mPascTabLayout;

    private PascTabLayout.OnTabSelectedListener onTabSelectedListener = new PascTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int index) {
            if (index == TAB_DESIGNER) {
                mViewPager.setCurrentItem(TAB_DESIGNER);
            } else if (index == TAB_DEVELOPER) {
                mViewPager.setCurrentItem(TAB_DEVELOPER);
            }
        }

        @Override
        public void onTabUnselected(int index) {

        }

        @Override
        public void onTabReselected(int index) {

        }

        @Override
        public void onDoubleTap(int index) {
            mPascTabLayout.clearSignCountView(index);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
        setupPagerAndTabs();
    }

    private void initView() {
        mViewPager = findViewById(R.id.ViewPager);
        mPascTabLayout = findViewById(R.id.PascTabLayout);
    }

    private void setupPagerAndTabs() {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new UICheckFragment());
        adapter.addFragment(new DemoMainFragment());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(TAB_DESIGNER);
        PascTabBuilder builder = mPascTabLayout.tabBuilder();
        builder.setTextSize(DensityUtils.sp2px(10), DensityUtils.sp2px(12))
                .setSelectedIconScale(1.2f)
                .setDynamicChangeIconColor(true);
        int normalSize = DensityUtils.dip2px(getContext(), 24);
        PascTab tabOne = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.main_tab_designer))
                .setNormalIconSizeInfo(normalSize, normalSize)
                .setText("Designer")
                .build();
        PascTab tabTwo = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.main_tab_developer))
                .setText("Developer")
                .setNormalIconSizeInfo(normalSize, normalSize)
                .build();
        mPascTabLayout
                .addTab(tabOne)
                .addTab(tabTwo);
        mPascTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        mPascTabLayout.setupWithViewPager(mViewPager, false);
    }

    private static class BottomAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        BottomAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
    }

}
