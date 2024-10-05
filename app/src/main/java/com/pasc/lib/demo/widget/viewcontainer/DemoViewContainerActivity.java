package com.pasc.lib.demo.widget.viewcontainer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.roundview.RoundLinearLayout;
import com.pasc.lib.widget.viewcontainer.ViewContainer;

public class DemoViewContainerActivity extends AppCompatActivity {

    private ViewContainer viewContainer;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_viewcontainer);
        viewContainer = findViewById(R.id.view_container);
        viewContainer.showLoading();
        handler=new Handler(){
            @Override public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        handler.postDelayed(runnable,3000);

    }
    Runnable runnable=new Runnable() {
        @Override public void run() {
            if(isFinishing())return;
            viewContainer.showAddLayout();
        }
    };

    @Override protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
