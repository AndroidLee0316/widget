package com.pasc.lib.demo.widget.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.common.PermissionDialogFragment;

//@Route(path = "/Demo/Dialogs/PermissionDialogFragment")
public class DemoPermissionDialogActivity extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_permission_dialg);
        initView();
    }

    private void initView(){


        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PermissionDialogFragment permissionDialogFragment = new PermissionDialogFragment.Builder()
                        .setTitle("开启定位")
                        .setDesc("立即获得天气、地图等服务")
                        .setImageRes(R.drawable.location_permission)
                        .setCancelable(false)
                        .setConfirmText("去开启")
                        .setOnConfirmListener(new OnConfirmListener<PermissionDialogFragment>() {
                            @Override
                            public void onConfirm(PermissionDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoPermissionDialogActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        }).setOnCloseListener(new OnCloseListener<PermissionDialogFragment>() {
                            @Override
                            public void onClose(PermissionDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoPermissionDialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                permissionDialogFragment.show(DemoPermissionDialogActivity.this, "ConfirmDialogFragment");
//                ButtonWrapper buttonWrapper = ButtonWrapper.wapButton("去开启", R.color.white, R.drawable.selector_primary_button);
//
//                List<CharSequence> items = new ArrayList<>();
//                items.add("我要吐槽");
//                items.add("下次再说");
//                items.add("去开启");
//
//                List<Integer> textColors = new ArrayList<>();
//                textColors.add(R.color.selector_secondary_button_text);
//                textColors.add(R.color.selector_secondary_button_text);
//                textColors.add(R.color.white);
//
//                List<Integer> buttonBgs = new ArrayList<>();
//                buttonBgs.add(R.drawable.selector_secondary_button);
//                buttonBgs.add(R.drawable.selector_secondary_button);
//                buttonBgs.add(R.drawable.selector_primary_button);
//                ButtonWrapper buttonWrapper2 = ButtonWrapper.wapButtons(items, textColors, buttonBgs);
//
//
//                final PermissionDialogFragment2 permissionDialogFragment = new PermissionDialogFragment2.Builder()
//                        .setTitle("开启定位")
//                        .setDesc("立即获得天气、地图等服务")
//                        .setIconResId(R.drawable.location_permission)
//                        .setCloseImgVisible(true)
//                        .setButton(buttonWrapper2, new DialogFragmentInterface.OnClickListener<PermissionDialogFragment2>() {
//                            @Override
//                            public void onClick(PermissionDialogFragment2 dialogFragment, int which) {
//                                Toast.makeText(DemoPermissionDialogActivity.this, items.get(which), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setOnCancelListener(new DialogFragmentInterface.OnCancelListener<PermissionDialogFragment2>() {
//                            @Override
//                            public void onCancel(PermissionDialogFragment2 dialogFragment) {
//                                dialogFragment.dismiss();
//                                Toast.makeText(DemoPermissionDialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .build();
//                permissionDialogFragment.show(DemoPermissionDialogActivity.this, "ConfirmDialogFragment");
            }
        });


        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PermissionDialogFragment permissionDialogFragment = new PermissionDialogFragment.Builder()
                        .setDesc("非常抱歉，因系统维护\n该服务暂时无法使用，敬请谅解")
                        .setImageRes(R.drawable.location_permission)
                        .setCancelable(false)
                        .setConfirmText("我知道了")
                        .setClosable(false)
                        .setTitleVisible(false)
                        .setOnConfirmListener(new OnConfirmListener<PermissionDialogFragment>() {
                            @Override
                            public void onConfirm(PermissionDialogFragment dialogFragment) {
                                Toast.makeText(DemoPermissionDialogActivity.this,"我知道了",Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build();
                permissionDialogFragment.show(DemoPermissionDialogActivity.this, "ConfirmDialogFragment");

            }
        });


    }
}