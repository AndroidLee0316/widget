package com.pasc.lib.demo.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.CreditDialView;
@Route(path = "/widget/demo/CreditDialView")
public class DemoCreditDialActivity extends AppCompatActivity {

    private CreditDialView creditDialView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_credit_dial);
        initView();

    }

    private void initView(){
        creditDialView = findViewById(R.id.cdv_score);
        creditDialView.setCurrentProgress(25);
        creditDialView.setCreditLevel("信用一般");
        creditDialView.setCreditScore("566");
        creditDialView.setDate("查询时间:2019-02-21");
//        creditDialView.invalidate();

    }
}
