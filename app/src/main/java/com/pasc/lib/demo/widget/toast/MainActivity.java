package com.pasc.lib.demo.widget.toast;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.toast.Toasty;

import static android.graphics.Typeface.BOLD_ITALIC;
@Route(path = "/Demo/Others/Toasty")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_toast);

        final MainActivity context = MainActivity.this;
        findViewById(R.id.button_error_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toasty.init(context)
                        .setMessage("这是一个错误信息")
                        .setAlpha(1f)
                        .withIcon(true)
                        .renderError()
                        .stayShort()
                        .onTop()
                        .show();
            }
        });
        findViewById(R.id.button_success_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.init(context)
                        .setMessage("哇！成功啦！")
                        .setAlpha(0.5f)
                        .withIcon(true)
                        .renderSuccess()
                        .stayShort()
                        .onCenter()
                        .show();
            }
        });
        findViewById(R.id.button_info_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.init(context)
                        .setMessage("这是info类消息")
                        .setAlpha(0.8f)
                        .withIcon(true)
                        .renderInfo()
                        .stayShort()
                        .onBottom()
                        .show();
            }
        });
        findViewById(R.id.button_warning_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.init(context)
                        .setMessage("这是warn类消息")
                        .setAlpha(0.8f)
                        .withIcon(true)
                        .renderWarn()
                        .stayShort()
                        .onBottom()
                        .show();
            }
        });
        findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.init(context)
                        .setMessage("该功能暂未开放，敬请期待")
                        .show();
            }
        });
        findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = getResources().getDrawable(R.drawable.ic_success);
                Toasty.init(context)
                        .setMessage("缓存清除成功")
                        .withSuccessIcon()
                        .show();
            }
        });
        findViewById(R.id.button_normal_toast_w_icon_fail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.init(context)
                        .setMessage("缓存清除失败")
                        .withErrorIcon()
                        .show();
            }
        });
        findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.init(context).setMessage(getFormattedMessage()).withIcon(true).show();
            }
        });

        findViewById(R.id.two_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.init(context)
                        .setMessage("为保障您的信息安全,请先输入您的账户信息。")
                        .show();
            }
        });
    }

    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
