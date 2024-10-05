package com.pasc.lib.demo.widget.button;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.button.PascToggleButton;

@Route(path = "/Demo/Widgets/PascToggleButton")
public class DemoPascToggleButtonActivity extends AppCompatActivity {
    private int mTempSelectColor;
    private PascToggleButton pascToggleButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_toggle_button);
        pascToggleButton = findViewById(R.id.toggle_btn);
    }

    public void selectColor(View view) {
        showColorDialog((TextView) view);
    }


    private void showColorDialog(TextView destView) {
        ColorDrawable background = (ColorDrawable) destView.getBackground();
        ColorPickerDialogBuilder.with(this)
                .setTitle("选择颜色")
                .initialColor(background.getColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int i) {
                        mTempSelectColor = i;
                    }
                })
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, Integer[] integers) {
                        destView.setBackgroundColor(mTempSelectColor);
                        if (destView.getId() == R.id.btn1) {
                            pascToggleButton.setOffBorderColor(mTempSelectColor);
                        } else if (destView.getId() == R.id.btn2) {
                            pascToggleButton.setOffColor(mTempSelectColor);
                        } else if (destView.getId() == R.id.btn3) {
                            pascToggleButton.setOnColor(mTempSelectColor);
                        } else if (destView.getId() == R.id.btn4) {
                            pascToggleButton.setSpotColor(mTempSelectColor);
                        }
                    }
                })
                .build().show();

    }
}
