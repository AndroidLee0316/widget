package com.pasc.lib.demo.widget.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/12
 */
@Route(path = "/Demo/Containers/PascTabLayout")
public class TabLayoutSampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_tablayout);
    }

    public void btn1Click(View view) {
        startActivity(new Intent(this, BottomNavigationBarSampleActivity.class));
    }

    public void btn2Click(View view) {
        startActivity(new Intent(this, SegmentedSampleActivity.class));
    }
}
