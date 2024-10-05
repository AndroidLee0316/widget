package com.pasc.lib.demo.widget.scrolltext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.roundview.RoundLinearLayout;

@Route(path = "/Demo/Widgets/ScrollTextView")
public class DemoScrollTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_scroll_text_view);
    }
}
