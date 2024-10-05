package com.pasc.lib.demo.widget.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnButtonClickListener;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.common.PraiseDialogFragment;

//@Route(path = "/Demo/Dialogs/PraiseDialogFragment")
public class DemoPraiseDialogActivity extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_praise_dialg);
        initView();
    }

    private void initView(){


        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PraiseDialogFragment praiseDialogFragment = new PraiseDialogFragment.Builder()
                        .setTitle("给个好评")
                        .setDesc("若对我们的服务满意，请给个好评")
                        .setImageRes(R.drawable.praise)
                        .setCancelable(true)
                        .setPrimaryButtonText("去开启")
                        .setPrimaryButtonBackground(com.pasc.lib.widget.R.drawable.selector_primary_button)
                        .setSecondaryButtonText("下次再说")
                        .setSecondaryButtonBackground(com.pasc.lib.widget.R.drawable.selector_secondary_button)
                        .setTertiaryButtonText("我要吐槽")
                        .setTertiaryButtonBackground(com.pasc.lib.widget.R.drawable.selector_secondary_button)
                        .setPrimaryButtonVisible(true)
                        .setSecondaryButtonVisible(true)
                        .setTertiaryButtonVisible(true)
                        .setPrimaryButtonOnClickListener(new OnButtonClickListener<PraiseDialogFragment>() {
                            @Override
                            public void onButtonClick(PraiseDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoPraiseDialogActivity.this,"去开启",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setSecondaryButtonOnClickListener(new OnButtonClickListener<PraiseDialogFragment>() {
                            @Override
                            public void onButtonClick(PraiseDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoPraiseDialogActivity.this,"下次再说",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setTertiaryButtonOnClickListener(new OnButtonClickListener<PraiseDialogFragment>() {
                            @Override
                            public void onButtonClick(PraiseDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoPraiseDialogActivity.this,"我要吐槽",Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setOnCloseListener(new OnCloseListener<PraiseDialogFragment>() {
                            @Override
                            public void onClose(PraiseDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoPraiseDialogActivity.this,"取消",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                praiseDialogFragment.show(DemoPraiseDialogActivity.this, "praiseDialogFragment");
            }
        });


        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }
}