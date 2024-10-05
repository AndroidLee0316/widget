package com.pasc.lib.demo.widget.tablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pasc.lib.demo.R;
import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.tablayout.PascTab;
import com.pasc.lib.widget.tablayout.PascTabBuilder;
import com.pasc.lib.widget.tablayout.PascTabLayout;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/12
 */
public class BottomNavigationBarSampleActivity extends AppCompatActivity {
    private PascTabLayout mPascTabLayout;
    private TextView mTextTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_tablayout_bottom_navigation_bar);
        mPascTabLayout = findViewById(R.id.PascTabLayout);
        mTextTv = findViewById(R.id.text);
        initTabs();
    }


    private void initTabs() {
        int normalColor = Color.parseColor("#666666");
        int selectColor = Color.parseColor("#27A5F9");
        PascTabBuilder builder = mPascTabLayout.tabBuilder();
        builder.setColor(normalColor, selectColor)
//                .setSelectedIconScale(1.2f)
//                .setTextSize(DensityUtils.sp2px(10), DensityUtils.sp2px(10))
                .setDynamicChangeIconColor(false);
        PascTab tabOne = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_one_normal))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_one_selected))
                .setText("首页")
                .build();
        PascTab tabTwo = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_normal))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_two_selected))
                .setText("政务")
                .build();
        PascTab tabThree = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_normal))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_three_selected))
                .setText("生活")
                .build();
        tabThree.setSignCount(10);

        PascTab tabFour = builder
                .setNormalDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_four_normal))
                .setSelectedDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_tablayout_bottom_four_selected))
                .setText("我的")
                .build();
        tabFour.setRedPoint();

        mPascTabLayout.addTab(tabOne)
                .addTab(tabTwo)
                .addTab(tabThree)
                .addTab(tabFour);

        mPascTabLayout.addOnTabSelectedListener(new PascTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                String text = "首页";
                if (index == 0) {
                    text = "首页";
                } else if (index == 1) {
                    text = "政务";
                } else if (index == 2) {
                    text = "生活";
                } else if (index == 3) {
                    text = "我的";
                }
                mTextTv.setText(text);
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
        mPascTabLayout.selectTab(0);
    }
}
