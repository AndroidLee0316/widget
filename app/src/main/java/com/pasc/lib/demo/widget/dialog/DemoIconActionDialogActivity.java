package com.pasc.lib.demo.widget.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;
import com.pasc.lib.widget.dialog.iconaction.IconActionDialogFragment;
import com.pasc.lib.widget.dialog.inset.InsetDialogFragment;
@Route(path = "/Demo/Dialogs/IconActionDialogFragment")
public class DemoIconActionDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_icon_action_dialog);

        findViewById(R.id.defaultButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IconActionDialogFragment.Builder()
                        .build()
                        .show(getSupportFragmentManager(), "IconActionDialogFragment");
            }
        });

        findViewById(R.id.setIconButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IconActionDialogFragment.Builder()
                        .addItem(R.drawable.ic_wechat, "微信")
                        .addItem(R.drawable.ic_qq, "QQ")
                        .build()
                        .show(getSupportFragmentManager(), "IconActionDialogFragment");
            }
        });

        findViewById(R.id.setTextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IconActionDialogFragment.Builder()
                        .addItem(R.drawable.ic_wechat, "微信")
                        .addItem(R.drawable.ic_qq, "QQ")
                        .addItem(R.drawable.ic_qq_zone, "QQ空间")
                        .setTitle("你想分享到哪？")
                        .setCloseText("关闭")
                        .build()
                        .show(getSupportFragmentManager(), "IconActionDialogFragment");
            }
        });

        findViewById(R.id.setListenerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IconActionDialogFragment.Builder()
                        .addItem(R.drawable.ic_wechat, "微信")
                        .addItem(R.drawable.ic_qq, "QQ")
                        .addItem(R.drawable.ic_qq_zone, "QQ空间")
                        .addItem(R.drawable.ic_wechat_friend_circle, "微信朋友圈")
                        .addItem(R.drawable.ic_qq, "新QQ")
                        .setTitle("你想分享到哪？")
                        .setCloseText("关闭")
                        .setOnSingleChoiceListener(new OnSingleChoiceListener<IconActionDialogFragment>() {
                            @Override
                            public void onSingleChoice(IconActionDialogFragment dialogFragment, int position) {
                                Toast.makeText(DemoIconActionDialogActivity.this, "position=" + position, Toast.LENGTH_LONG).show();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<IconActionDialogFragment>() {
                            @Override
                            public void onClose(IconActionDialogFragment dialogFragment) {
                                Toast.makeText(DemoIconActionDialogActivity.this, "点击了关闭按钮", Toast.LENGTH_LONG).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build()
                        .show(getSupportFragmentManager(), "IconActionDialogFragment");
            }
        });
    }
}
