package com.pasc.lib.demo.widget.toolbar;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.ICallBack;
import com.pasc.lib.widget.toolbar.PascToolbar;
@Route(path = "/Demo/Containers/PascToolbar")
public class ToolbarActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_toolbar);
    Window window = getWindow();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.getDecorView()
              .setSystemUiVisibility(
                      View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
    }

    PascToolbar toolbar = findViewById(R.id.toolbar);
    toolbar.addCloseImageButton().setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(ToolbarActivity.this, "返回", Toast.LENGTH_SHORT).show();
      }
    });
    toolbar.addRightTextButton("操作").setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(ToolbarActivity.this, "操作", Toast.LENGTH_SHORT).show();
      }
    });

    PascToolbar toolbar1 = findViewById(R.id.toolbar1);
    toolbar1.addCloseImageButton();
    toolbar1.addLeftTextButton("左菜单").setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(ToolbarActivity.this, "左菜单", Toast.LENGTH_SHORT).show();
      }
    });
    toolbar1.addRightImageButton(R.drawable.ic_nav_share)
            .setOnClickListener(new View.OnClickListener() {
              @Override public void onClick(View v) {
                Toast.makeText(ToolbarActivity.this, "分享", Toast.LENGTH_SHORT).show();
              }
            });
    toolbar1.addRightTextButton("右菜单").setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(ToolbarActivity.this, "右菜单", Toast.LENGTH_SHORT).show();
      }
    });

    PascToolbar toolbar2 = findViewById(R.id.toolbar2);
    toolbar2.setTitle("标题");
    toolbar2.setSubTitle("子标题");
    toolbar2.setBackIcon(R.drawable.ic_back_black);
    toolbar2.setCloseIcon(R.drawable.nav_close);
    toolbar2.setActionText("操作");
    toolbar2.setActionIcon(R.drawable.ic_nav_share);
//    toolbar2.setNavViewVisible(true);
//    toolbar2.setTitleViewVisible(true);
//    toolbar2.setActionViewVisible(true);

    final View toolbarLeft = LayoutInflater.from(this)
            .inflate(R.layout.toolbar_left, null);
//    final View toolbarTitle = LayoutInflater.from(this)
//            .inflate(R.layout.toolbar_title, null);
//    final View toolbarAction = LayoutInflater.from(this)
//            .inflate(R.layout.toolbar_action, null);
    toolbarLeft.findViewById(R.id.tips_tv).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ToolbarActivity.this, "自定义布局", Toast.LENGTH_SHORT).show();
      }
    });
    toolbar2.setNavView(toolbarLeft);
//    toolbar2.setTitleView(toolbarTitle);
//    toolbar2.setActionView(toolbarAction);
    toolbar2.setBackIconClickListener(new ICallBack() {
      @Override
      public void callBack() {
        Toast.makeText(ToolbarActivity.this, "返回按钮点击事件", Toast.LENGTH_SHORT).show();

      }
    });

    toolbar2.setCloseIconClickListener(new ICallBack() {
      @Override
      public void callBack() {
        Toast.makeText(ToolbarActivity.this, "关闭按钮点击事件", Toast.LENGTH_SHORT).show();
      }
    });
    toolbar2.setActionClickListener(new ICallBack() {
      @Override
      public void callBack() {
        Toast.makeText(ToolbarActivity.this, "操作点击事件", Toast.LENGTH_SHORT).show();
      }
    });

  }
}
