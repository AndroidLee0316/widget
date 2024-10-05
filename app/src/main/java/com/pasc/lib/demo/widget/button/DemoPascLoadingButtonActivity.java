package com.pasc.lib.demo.widget.button;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.button.PascLoadingButton;

@Route(path = "/Demo/Widgets/PascLoadingButton")
public class DemoPascLoadingButtonActivity extends AppCompatActivity{

    private Handler handler;
    PascLoadingButton buttonPrimary,buttonSecondary,
            buttonTertiary,buttonShort;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_loading_button);
        buttonPrimary = findViewById(R.id.bt_primary);
        buttonSecondary = findViewById(R.id.bt_secondary);
        buttonTertiary = findViewById(R.id.bt_tertiary);
        buttonShort= findViewById(R.id.bt_short);
        buttonPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPrimary.startLoading();
                handler=new Handler(){
                    @Override public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                handler.postDelayed(runnable1,5000);

            }
        });
        buttonSecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSecondary.startLoading();
                handler=new Handler(){
                    @Override public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                handler.postDelayed(runnableSecondary,5000);

            }
        });
        buttonTertiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonTertiary.startLoading();
                handler=new Handler(){
                    @Override public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                handler.postDelayed(runnableTertiary,5000);

            }
        });


        buttonShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonShort.startLoading();
                buttonShort.setTextVisible(false);
                handler=new Handler(){
                    @Override public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                handler.postDelayed(runnable2,5000);
            }
        });
    }
    Runnable runnable1=new Runnable() {
        @Override public void run() {
            buttonPrimary.stopLoading();
        }
    };
    Runnable runnableSecondary=new Runnable() {
        @Override public void run() {
            buttonSecondary.stopLoading();
        }
    };
    Runnable runnableTertiary=new Runnable() {
        @Override public void run() {
            buttonTertiary.stopLoading();
        }
    };
    Runnable runnable2=new Runnable() {
        @Override public void run() {
            buttonShort.stopLoading();
            buttonShort.setTextVisible(true);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
