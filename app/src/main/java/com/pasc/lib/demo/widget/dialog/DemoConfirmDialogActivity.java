package com.pasc.lib.demo.widget.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmChoiceStateListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnDismissListener;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;
import com.pasc.lib.widget.dialog.common.AnimationType;
import com.pasc.lib.widget.dialog.common.PermissionDialogFragment;
import com.pasc.lib.widget.dialog.common.ConfirmDialogFragment;

import java.util.ArrayList;

import static com.pasc.lib.widget.dialog.BaseDialogFragment.ARG_ON_DISMISS_LISTENER;
import static com.pasc.lib.widget.dialog.BaseDialogFragment.WHAT_ON_DISMISS_LISTENER;

@Route(path = "/Demo/Dialogs/ConfirmDialogFragment")
public class DemoConfirmDialogActivity extends AppCompatActivity {

    private Button mIsConfirmDialog,mIsConfirmDialog1,mTipsConfirmDialog,mTipsConfirmDialog1;

    //定位对话框
    private Button mLocationDialog,mAuthorityManagementDialog;

    private Button allDialogAttribute;

    //listdialog
    private Button mListChoiceDialog;

    private Button mCustomDialog;
    private Button mStartDialog;
    private Button mMoreButtonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_common_warning_dialg);
        initView();
    }

    private void initView(){

        mIsConfirmDialog = findViewById(R.id.isconfirmdialog);
        mIsConfirmDialog1 = findViewById(R.id.isconfirmdialog1);

        mTipsConfirmDialog = findViewById(R.id.tipsconfirmdialog);
        mTipsConfirmDialog1 = findViewById(R.id.tipsconfirmdialog1);

        mLocationDialog = findViewById(R.id.location_dialog);

        mAuthorityManagementDialog = findViewById(R.id.authority_management_dialog);

        mListChoiceDialog = findViewById(R.id.list_Choice_dialog);

        allDialogAttribute = findViewById(R.id.all_attribute_dialog);

        mCustomDialog= findViewById(R.id.custom_dialog);

        mStartDialog = findViewById(R.id.startdialog);
        mMoreButtonDialog = findViewById(R.id.more_button);

         findViewById(R.id.tell_dialog).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 final ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment.Builder()
                         .setDesc("15017912345")
                         .setAnimationType(AnimationType.TRANSLATE_BOTTOM)
                         .setConfirmText("呼叫")
                         .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
                             @Override
                             public void onConfirm(ConfirmDialogFragment dialogFragment) {
                                 dialogFragment.dismiss();
                             }
                         })
                         .setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
                             @Override
                             public void onClose(ConfirmDialogFragment dialogFragment) {
                                 dialogFragment.dismiss();
                             }
                         })
                         .build();
                 confirmDialogFragment.show(DemoConfirmDialogActivity.this, "ConfirmDialogFragment");
             }
         });

        mStartDialog.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(DemoConfirmDialogActivity.this,"确定",Toast.LENGTH_SHORT).show();
                            }
                        }).setOnCloseListener(new OnCloseListener<PermissionDialogFragment>() {
                            @Override
                            public void onClose(PermissionDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoConfirmDialogActivity.this,"取消",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                permissionDialogFragment.show(DemoConfirmDialogActivity.this, "ConfirmDialogFragment");
            }
        });


        mMoreButtonDialog.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(DemoConfirmDialogActivity.this,"我知道了",Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build();
                permissionDialogFragment.show(DemoConfirmDialogActivity.this, "ConfirmDialogFragment");


            }
        });

        mIsConfirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .setTitle("确定要删除该地址吗？")
                        .setCancelable(false)
                        .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoConfirmDialogActivity.this,"确定",Toast.LENGTH_SHORT).show();
                            }
                        }).setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
                            @Override
                            public void onClose(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoConfirmDialogActivity.this,"取消",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                commonDialog.getArguments().putParcelable(ARG_ON_DISMISS_LISTENER, commonDialog.obtainMessage(WHAT_ON_DISMISS_LISTENER, new OnDismissListener<ConfirmDialogFragment>() {
                    @Override
                    public void onDismiss(ConfirmDialogFragment dialogFragment) {
                        Toast.makeText(DemoConfirmDialogActivity.this,"对话框消失了！",Toast.LENGTH_SHORT).show();
                    }
                }));

//                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");
                commonDialog.show(DemoConfirmDialogActivity.this, "ConfirmDialogFragment");

            }
        });

        mIsConfirmDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .setDesc("我是文字我是文字我是文字文字我是文字我是文字我是文字文字我是文字我是文字我是文字文字我是文字我是文字我是文字文字我是文字我是文字我是文字文字我是文字我是文字我是文字文字我是文字我是文字我是文字文字" +
                                "我是文字我是文字我是文字文字我是文字我是文字我是文字文字我是文字我是文字我是文字文字")
                        .setDescLineSpacingExtra(10)
                        .setConfirmTextColor(getResources().getColor(R.color.positive_text_Color))
                        .setCloseTextColor(getResources().getColor(R.color.positive_text_Color))
                        .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoConfirmDialogActivity.this,"确定",Toast.LENGTH_SHORT).show();
                            }
                        }).setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
                            @Override
                            public void onClose(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoConfirmDialogActivity.this,"取消",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");

            }
        });

        mTipsConfirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .isEnableNoLongerAsked(true)
                        .setHideCloseButton(true)
                        .setTitle("提示")
                        .setDesc("提示内容提示内容提示内容")
                        .setConfirmTextColor(getResources().getColor(R.color.positive_text_Color))
                        .setOnConfirmChoiceStateListener(new OnConfirmChoiceStateListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment, boolean checkState) {

                                String ss = "false";
                                if(checkState){
                                    ss = "true";
                                }
                                Toast.makeText(DemoConfirmDialogActivity.this,ss,Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");


            }
        });


        mTipsConfirmDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .setTitle("提示")
                        .setDesc("“我的深圳”手机号码与深圳交警星级用户手机号码不一致。")
                        .setConfirmTextColor(getResources().getColor(R.color.positive_text_Color))
                        .setCloseTextColor(getResources().getColor(R.color.location_negative_Color))
                        .setOnConfirmChoiceStateListener(new OnConfirmChoiceStateListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment, boolean checkState) {

                                String ss = "false";
                                if(checkState){
                                    ss = "true";
                                }
                                Toast.makeText(DemoConfirmDialogActivity.this,ss,Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");


            }
        });

        mLocationDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .setTitle("开启定位")
                        .setTitleColor(getResources().getColor(R.color.location_title_Color))
                        .setTitleSize(20)
                        .setDesc("立即获得天气、地图等服务")
                        .setDescColor(getResources().getColor(R.color.location_desc_Color))
                        .setConfirmText("立即开启")
                        .setConfirmTextColor(getResources().getColor(R.color.positive_text_Color))
                        .setCloseTextColor(getResources().getColor(R.color.location_negative_Color))
                        .setImageRes(R.drawable.weather)
                        .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
//                                Toast.makeText(DemoConfirmDialogActivity.this,"确定",Toast.LENGTH_SHORT).show();
                            }
                        }).setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
                            @Override
                            public void onClose(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
//                                Toast.makeText(DemoConfirmDialogActivity.this,"取消",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");

            }
        });

        mAuthorityManagementDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .setDesc("我们需要相机权限为您提供服务，是否去设置？")
                        .setDescColor(getResources().getColor(R.color.authority_desc_Color))
                        .setConfirmText("权限设置")
                        .setConfirmTextColor(getResources().getColor(R.color.positive_text_Color))
                        .setCloseTextColor(getResources().getColor(R.color.location_negative_Color))
                        .setImageRes(R.drawable.authorization)
                        .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
//                                Toast.makeText(DemoConfirmDialogActivity.this,"确定",Toast.LENGTH_SHORT).show();
                            }
                        }).setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
                            @Override
                            public void onClose(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
//                                Toast.makeText(DemoConfirmDialogActivity.this,"取消",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");


            }
        });


        allDialogAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .setTitle("标题")
                        .setTitleColor(getResources().getColor(R.color.location_title_Color))
                        .setTitleSize(15)
                        .setDesc("立即获得天气、地图等服务")
                        .setDescColor(getResources().getColor(R.color.location_desc_Color))
                        .setDescSize(15)
                        .isEnableNoLongerAsked(true)
                        .setConfirmText("立即开启")
                        .setConfirmTextColor(getResources().getColor(R.color.positive_text_Color))
                        .setConfirmTextSize(15)
                        .setCloseText("取消")
                        .setCloseTextColor(getResources().getColor(R.color.location_negative_Color))
                        .setCloseTextSize(15)
                        .setHideCloseButton(true)
                        .setImageRes(R.drawable.weather)
                        .setOnConfirmChoiceStateListener(new OnConfirmChoiceStateListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment, boolean checkState) {
                                dialogFragment.dismiss();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");



            }
        });

        mCustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ConfirmDialogFragment commonDialog = new ConfirmDialogFragment.Builder()
                        .setTitle("遇到问题？")
                        .setDesc("找回账号（手机号码已弃用）")
                        .setHideConfirmButton(true)
                        .setCloseText("取消")
                        .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
                            @Override
                            public void onConfirm(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoConfirmDialogActivity.this,"确定",Toast.LENGTH_SHORT).show();
                            }
                        }).setOnCloseListener(new OnCloseListener<ConfirmDialogFragment>() {
                            @Override
                            public void onClose(ConfirmDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                                Toast.makeText(DemoConfirmDialogActivity.this,"取消",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");



            }
        });


        mListChoiceDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(DemoConfirmDialogActivity.this,DemoChoiceDialogActivity.class);
                startActivity(intent);

            }
        });

    }
}