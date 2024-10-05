package com.pasc.lib.demo.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pasc.lib.demo.R;
import com.pasc.lib.demo.widget.dialog.DemoChoiceDialogActivity;
import com.pasc.lib.widget.EmptyView;
import com.pasc.lib.widget.FaceCircleProcessView;
import com.pasc.lib.widget.ICallBack;

public class DemoEmptyViewActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private EmptyView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_empty);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        emptyView = findViewById(R.id.empty_view);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyView.setErrorIconRes(R.drawable.ic_common_error);
                emptyView.setErrorTips("error");
                emptyView.setRetryText("error");
//                emptyView.setLoadingLayoutIsVisible(View.VISIBLE);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyView.showDefaultNoNetWork(new ICallBack() {
                    @Override
                    public void callBack() {
                        Toast.makeText(DemoEmptyViewActivity.this,
                                "没有网络了",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
