package com.pasc.lib.demo.widget.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnDismissListener;
import com.pasc.lib.widget.dialog.inset.InsetDialogFragment;
import com.pasc.lib.widget.dialog.loading.LoadingDialogFragment;
@Route(path = "/Demo/Dialogs/InsetDialogFragment")
public class DemoInsetDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_inset_dialog);

        findViewById(R.id.defaultButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsetDialogFragment.Builder()
                        .build()
                        .show(getSupportFragmentManager(), "InsetDialogFragment");
            }
        });

        findViewById(R.id.changeDescButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsetDialogFragment.Builder()
                        .setDesc("优化社保查询功能")
                        .setConfirmText("立即升级")
                        .build()
                        .show(DemoInsetDialogActivity.this, "InsetDialogFragment");
                // .show(getSupportFragmentManager(), "InsetDialogFragment");
            }
        });

        findViewById(R.id.multiLineDescButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsetDialogFragment.Builder()
                        .setDesc("1.优化社保查询功能；\n" +
                                "2.新增违章查询功能；\n" +
                                "3.更新反馈输入错误、提交失败等报错提问；\n" +
                                "4.优化社保查询功能；\n" +
                                "5.新增违章查询功能；\n" +
                                "6.优化社保查询功能；\n" +
                                "7.新增违章查询功能；\n" +
                                "8.更新反馈输入错误、提交失败等报错提问；\n" +
                                "9.优化社保查询功能；\n" +
                                "10.新增违章查询功能；")
                        .setConfirmText("立即升级")
                        .build()
                        .show(getSupportFragmentManager(), "InsetDialogFragment");
            }
        });

        findViewById(R.id.changeImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsetDialogFragment.Builder()
                        .setDesc("1.优化社保查询功能；\n" +
                                "2.新增违章查询功能；\n" +
                                "3.更新反馈输入错误、提交失败等报错提问；\n" +
                                "4.优化社保查询功能；\n" +
                                "5.新增违章查询功能；")
                        .setConfirmText("立即升级")
                        .setImageRes(R.drawable.bg_not_food)
                        .setOnConfirmListener(new OnConfirmListener<InsetDialogFragment>() {
                            @Override
                            public void onConfirm(InsetDialogFragment dialogFragment) {
                                Toast.makeText(DemoInsetDialogActivity.this, "点击了立即升级", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<InsetDialogFragment>() {
                            @Override
                            public void onClose(InsetDialogFragment dialogFragment) {
                                Toast.makeText(DemoInsetDialogActivity.this, "点击了关闭", Toast.LENGTH_LONG).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build()
                        .show(getSupportFragmentManager(), "InsetDialogFragment");
            }
        });
    }
}
