package com.pasc.lib.demo.widget.roundview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.roundview.RoundLinearLayout;
import com.pasc.lib.widget.roundview.RoundTextView;
@Route(path = "/Demo/Widgets/RoundLinearLayout")
public class DemoRoundViewActivity extends AppCompatActivity {

    private RoundLinearLayout roundTextLin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_round_view);
        roundTextLin = findViewById(R.id.round_lin);
        setRoundLinearLayout();


    }
    private void setRoundLinearLayout(){
        roundTextLin.getDelegate().setBackgroundColor(getResources().getColor(R.color.round_view_color));
        roundTextLin.getDelegate().setCornerRadius(5);
    }
}
