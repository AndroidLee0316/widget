package com.pasc.lib.demo.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.CallCarView;

public class DemoCallCardViewActivity extends AppCompatActivity implements CallCarView.OnCallCarListener {
    CallCarView viewCallCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_call_car);
        viewCallCar = findViewById(R.id.view_call_car);
        viewCallCar.setOnCallCarListener(this);
    }

    @Override
    public void onStartAddrClick() {

    }

    @Override
    public void onEndAddrClick() {

    }

    @Override
    public void onCallCarClick() {

    }

    @Override
    public void onCancelCarClick() {

    }

    @Override
    public void onClickEditRoute() {

    }
}
