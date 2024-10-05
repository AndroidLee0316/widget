package com.pasc.lib.demo.widget.dialog;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.DialogFragmentInterface;
import com.pasc.lib.widget.dialog.common.ButtonWrapper;
import com.pasc.lib.widget.dialog.common.PermissionDialogFragment2;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/Demo/Dialogs/PermissionDialogFragment2")
public class DemoPermissionDialog2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_permission_dialg2);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button0) {
                    //开启定位
                    ButtonWrapper buttonWrapper = ButtonWrapper.wapButton("去开启", R.color.white, R.drawable.selector_primary_button);
                    showPermissionDialog("开启定位", "立即获得天气、地图等服务", R.drawable.ic_permisson_location, buttonWrapper, true);
                } else if (v.getId() == R.id.button1) {
                    //开启功能名称
                    ButtonWrapper buttonWrapper = ButtonWrapper.wapButton("去开启", R.color.white, R.drawable.selector_primary_button);
                    showPermissionDialog("开启功能名称", "为您提供更完善的服务", R.drawable.ic_permisson_function, buttonWrapper, true);
                } else if (v.getId() == R.id.button2) {
                    //开启相机
                    ButtonWrapper buttonWrapper = ButtonWrapper.wapButton("去开启", R.color.white, R.drawable.selector_primary_button);
                    showPermissionDialog("开启相机", "为您提供更完善的服务", R.drawable.ic_permisson_camera, buttonWrapper, true);
                } else if (v.getId() == R.id.button3) {
                    //开启存储
                    ButtonWrapper buttonWrapper = ButtonWrapper.wapButton("去开启", R.color.white, R.drawable.selector_primary_button);
                    showPermissionDialog("开启存储", "为您提供更完善的服务", R.drawable.ic_permisson_storage, buttonWrapper, true);
                } else if (v.getId() == R.id.button4) {
                    //给个好评
                    List<CharSequence> items = new ArrayList<>();
                    items.add("我要吐槽");
                    items.add("下次再说");
                    items.add("去开启");

                    List<Integer> textColors = new ArrayList<>();
                    textColors.add(R.color.selector_secondary_button_text);
                    textColors.add(R.color.selector_secondary_button_text);
                    textColors.add(R.color.white);

                    List<Integer> buttonBgs = new ArrayList<>();
                    buttonBgs.add(R.drawable.selector_secondary_button);
                    buttonBgs.add(R.drawable.selector_secondary_button);
                    buttonBgs.add(R.drawable.selector_primary_button);

                    ButtonWrapper buttonWrapper = ButtonWrapper.wapButtons(items, textColors, buttonBgs);

                    showPermissionDialog("给个好评", "若对我们的服务满意，请给个好评", R.drawable.ic_permisson_praise, buttonWrapper, false);
                } else if (v.getId() == R.id.button5) {
                    //开启电话
                    ButtonWrapper buttonWrapper = ButtonWrapper.wapButton("去开启", R.color.white, R.drawable.selector_primary_button);
                    showPermissionDialog("开启电话", "为您提供更完善的服务", R.drawable.ic_permisson_phone, buttonWrapper, true);
                }
            }
        };
        findViewById(R.id.button0).setOnClickListener(onClickListener);
        findViewById(R.id.button1).setOnClickListener(onClickListener);
        findViewById(R.id.button2).setOnClickListener(onClickListener);
        findViewById(R.id.button3).setOnClickListener(onClickListener);
        findViewById(R.id.button4).setOnClickListener(onClickListener);
        findViewById(R.id.button5).setOnClickListener(onClickListener);
    }


    /**
     * 权限开启型弹窗
     *
     * @param title         弹窗标题
     * @param desc          弹窗描述
     * @param iconResId     弹窗icon
     * @param buttonWrapper 弹窗button包装类
     */
    private void showPermissionDialog(String title, String desc, @DrawableRes int iconResId, ButtonWrapper buttonWrapper, boolean closeImgVisible) {
        final PermissionDialogFragment2 permissionDialogFragment = new PermissionDialogFragment2.Builder()
                .setTitle(title)
                .setCloseImgVisible(closeImgVisible)
                .setDesc(desc)
                .setIconResId(iconResId)
                .setButton(buttonWrapper, new DialogFragmentInterface.OnClickListener<PermissionDialogFragment2>() {
                    @Override
                    public void onClick(PermissionDialogFragment2 dialogFragment, int which) {
                        Toast.makeText(DemoPermissionDialog2Activity.this, buttonWrapper.getItems().get(which), Toast.LENGTH_SHORT).show();
                        dialogFragment.dismiss();
                    }
                })
                .setOnCancelListener(new DialogFragmentInterface.OnCancelListener<PermissionDialogFragment2>() {
                    @Override
                    public void onCancel(PermissionDialogFragment2 dialogFragment) {
                        dialogFragment.dismiss();
                        Toast.makeText(DemoPermissionDialog2Activity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        permissionDialogFragment.show(DemoPermissionDialog2Activity.this, "ConfirmDialogFragment");
    }
}