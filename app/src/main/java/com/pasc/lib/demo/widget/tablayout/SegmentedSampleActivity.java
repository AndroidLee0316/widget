package com.pasc.lib.demo.widget.tablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasc.lib.demo.R;
import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.tablayout.PascTab;
import com.pasc.lib.widget.tablayout.PascTabBuilder;
import com.pasc.lib.widget.tablayout.PascTabIndicator;
import com.pasc.lib.widget.tablayout.PascTabLayout;

import java.util.HashMap;
import java.util.Map;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/12
 */
public class SegmentedSampleActivity extends AppCompatActivity {
    private PascTabLayout mPascTabLayout;
    private Toolbar mToolBar;
    private ViewPager mContentViewPager;
    private Map<ContentPage, View> mPageMap = new HashMap<>();
    private ContentPage mDestPage = ContentPage.Item1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_tablayout_segmented);
        mPascTabLayout = findViewById(R.id.PascTabLayout);
        mContentViewPager = findViewById(R.id.ViewPager);
        //获取到ToolBar的id
        mToolBar = findViewById(R.id.Toolbar);
        //设置ToolBar
        setSupportActionBar(mToolBar);
        //设置NavigationIcon的监听事件
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initTabAndPager();
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return ContentPage.SIZE;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            ContentPage page = ContentPage.getPage(position);
            View view = getPageView(page);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    };

    private void initTabAndPager() {
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mDestPage.getPosition(), false);
        PascTabBuilder builder = mPascTabLayout.tabBuilder();
        mPascTabLayout.addTab(builder.setText(getString(R.string.tabSegment_item_1_title)).build());
        mPascTabLayout.addTab(builder.setText(getString(R.string.tabSegment_item_2_title)).build());
        mPascTabLayout.setupWithViewPager(mContentViewPager, false);
        mPascTabLayout.setMode(PascTabLayout.MODE_FIXED);
        mPascTabLayout.addOnTabSelectedListener(new PascTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {

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
        });
    }

    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(Color.parseColor("#999999"));

            if (page == ContentPage.Item1) {
                textView.setText(R.string.tabSegment_item_1_content);
            } else if (page == ContentPage.Item2) {
                textView.setText(R.string.tabSegment_item_2_content);
            }

            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }

    public enum ContentPage {
        Item1(0),
        Item2(1);
        public static final int SIZE = 2;
        private final int position;

        ContentPage(int pos) {
            position = pos;
        }

        public static ContentPage getPage(int position) {
            switch (position) {
                case 0:
                    return Item1;
                case 1:
                    return Item2;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tablayout_fix, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PascTabBuilder tabBuilder = mPascTabLayout.tabBuilder()
                .setGravity(Gravity.CENTER);
        int indicatorHeight = DensityUtils.dp2px(2);
        switch (item.getItemId()) {
            case R.id.tabSegment_mode_general:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(null);
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_1_title)).build());
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_2_title)).build());
                break;
            case R.id.tabSegment_mode_bottom_indicator:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(new PascTabIndicator(
                        indicatorHeight, false, true));
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_1_title)).build());
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_2_title)).build());
                break;
            case R.id.tabSegment_mode_top_indicator:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(new PascTabIndicator(
                        indicatorHeight, true, true));
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_1_title)).build());
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_2_title)).build());
                break;
            case R.id.tabSegment_mode_indicator_with_content:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(new PascTabIndicator(
                        indicatorHeight, false, false));
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_1_title)).build());
                mPascTabLayout.addTab(tabBuilder.setText(getString(R.string.tabSegment_item_2_title)).build());
                break;
            case R.id.tabSegment_mode_left_icon_and_auto_tint:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(null);
                tabBuilder.setDynamicChangeIconColor(true);
                PascTab component = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_selected))
                        .setText("政务")
                        .build();
                PascTab util = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_selected))
                        .setText("生活")
                        .build();
                mPascTabLayout.addTab(component);
                mPascTabLayout.addTab(util);
                break;
            case R.id.tabSegment_mode_sign_count:
                //mPascTabLayout.showSignCountView(getContext(), 0, 20); // 也可以直接调用这个
                PascTab tab = mPascTabLayout.getTab(0);
                tab.setSignCount(20);

                PascTab tab1 = mPascTabLayout.getTab(1);
                tab1.setRedPoint();
                break;
            case R.id.tabSegment_mode_icon_change:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(null);
                tabBuilder.setDynamicChangeIconColor(false);
                PascTab component2 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_selected))
                        .setText("政务")
                        .build();
                PascTab util2 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_selected))
                        .setText("生活")
                        .build();
                mPascTabLayout.addTab(component2);
                mPascTabLayout.addTab(util2);
                break;
            case R.id.tabSegment_mode_muti_color:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(new PascTabIndicator(
                        indicatorHeight, false, true));
                tabBuilder.setDynamicChangeIconColor(true);
                PascTab component3 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_selected))
                        .setText("政务")
                        .setColor(Color.parseColor("#666666"), Color.parseColor("#27A5F9"))
                        .build();
                PascTab util3 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_selected))
                        .setText("生活")
                        .setColor(Color.parseColor("#999999"), Color.parseColor("#00FF7F"))
                        .build();
                mPascTabLayout.addTab(component3);
                mPascTabLayout.addTab(util3);
                break;
            case R.id.tabSegment_mode_change_content_by_index:
                mPascTabLayout.updateTabText(0, "动态更新文案");
                break;
            case R.id.tabSegment_mode_replace_tab_by_index:
                PascTab newTab = tabBuilder.setText("动态更新")
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_normal))
                        .setDynamicChangeIconColor(true)
                        .build();
                mPascTabLayout.replaceTab(0, newTab);
                break;
            case R.id.tabSegment_mode_scale_selected:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(new PascTabIndicator(
                        indicatorHeight, false, true));
                tabBuilder.setDynamicChangeIconColor(true)
                        .setTextSize(DensityUtils.sp2px(13), DensityUtils.sp2px(15))
                        .setSelectedIconScale(1.5f);
                PascTab component4 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_selected))
                        .setText("政务")
                        .setColor(Color.parseColor("#666666"), Color.parseColor("#27A5F9"))
                        .build();
                PascTab util4 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_selected))
                        .setText("生活")
                        .setColor(Color.parseColor("#999999"), Color.parseColor("#00FF7F"))
                        .build();
                mPascTabLayout.addTab(component4);
                mPascTabLayout.addTab(util4);
                break;
            case R.id.tabSegment_mode_change_gravity:
                mPascTabLayout.reset();
                mPascTabLayout.setIndicator(new PascTabIndicator(
                        indicatorHeight, false, true));
                tabBuilder.setDynamicChangeIconColor(true)
                        .setTextSize(DensityUtils.sp2px(13), DensityUtils.sp2px(15))
                        .setSelectedIconScale(1.5f)
                        .setGravity(Gravity.LEFT | Gravity.BOTTOM);
                PascTab component5 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_selected))
                        .setText("政务")
                        .setColor(Color.parseColor("#666666"), Color.parseColor("#27A5F9"))
                        .build();
                PascTab util5 = tabBuilder
                        .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_normal))
                        .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_selected))
                        .setText("生活")
                        .setColor(Color.parseColor("#999999"), Color.parseColor("#00FF7F"))
                        .build();
                mPascTabLayout.addTab(component5);
                mPascTabLayout.addTab(util5);
                break;
            default:
                break;
        }
        mPascTabLayout.notifyDataChanged();
        return true;
    }

}
