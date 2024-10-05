package com.pasc.lib.demo.widget.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;
import com.pasc.lib.widget.dialog.bottomchoice.BottomChoiceDialogFragment;

import java.util.ArrayList;
@Route(path = "/Demo/Dialogs/BottomChoiceDialogFragment")
public class DemoBottomChoiceDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_bottom_choice_dialog);

        findViewById(R.id.defaultButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomChoiceDialogFragment.Builder()
                        .setTitle("请选择")
                        .build()
                        .show(getSupportFragmentManager(), "BottomChoiceDialogFragment");
            }
        });

        findViewById(R.id.setItemsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CharSequence> items = new ArrayList<>();
                String source = "选项一（警示项）";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4D4F")), 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                items.add(spannableString);
                items.add("选项二");
                items.add("选项三");
                new BottomChoiceDialogFragment.Builder()
                        .setItems(items)
                        .build()
                        .show(getSupportFragmentManager(), "BottomChoiceDialogFragment");
            }
        });

        findViewById(R.id.setTextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CharSequence> items = new ArrayList<>();
                String source = "选项一（警示项）";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4D4F")), 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                items.add(spannableString);
                items.add("选项二");
                items.add("选项三");
                items.add("选项四\n描述");
                new BottomChoiceDialogFragment.Builder()
                        .setItems(items)
                        .setTitle("是否确认退出登录？是否确认退出登录？是否确认退出登录？是否确认退出")
                        .setCloseText("关闭")
                        .build()
                        .show(getSupportFragmentManager(), "BottomChoiceDialogFragment");
            }
        });

        findViewById(R.id.setListenerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CharSequence> items = new ArrayList<>();
                String source = "选项一（警示项）";
                SpannableString spannableString = new SpannableString(source);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4D4F")), 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                items.add(spannableString);
                items.add("选项二");
                items.add("选项三");
                new BottomChoiceDialogFragment.Builder()
                        .setItems(items)
                        .setTitle("是否确认退出登录？")
                        .setCloseText("关闭")
                        .setOnSingleChoiceListener(new OnSingleChoiceListener<BottomChoiceDialogFragment>() {
                            @Override
                            public void onSingleChoice(BottomChoiceDialogFragment dialogFragment, int position) {
                                Toast.makeText(DemoBottomChoiceDialogActivity.this, "position=" + position, Toast.LENGTH_LONG).show();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<BottomChoiceDialogFragment>() {

                            @Override
                            public void onClose(BottomChoiceDialogFragment dialogFragment) {
                                Toast.makeText(DemoBottomChoiceDialogActivity.this, "点击了关闭按钮", Toast.LENGTH_LONG).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build()
                        .show(getSupportFragmentManager(), "BottomChoiceDialogFragment");
            }
        });
    }
}
