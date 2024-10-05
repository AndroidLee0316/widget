package com.pasc.lib.widget.dialognt;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.ScreenUtils;
/**
 * 已经废弃掉，改用ConfirmDialogFragment
 */
@Deprecated
public class TelDialog extends Dialog {
    TextView tvConfirm;
    TextView tvCancel;
    TextView tvContent;

    public TelDialog(@NonNull Context context, String tel) {
        super(context, R.style.style_dialog_select_item);
        int widthPixels = ScreenUtils.getScreenWidth();
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_tel, null);
        setContentView(contentView);
        tvContent = findViewById(R.id.tv_content);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvCancel = findViewById(R.id.tv_cancel);
        tvContent.setText(tel);
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = widthPixels - DensityUtils.dp2px(100);
        contentView.setLayoutParams(params);

        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    /**
     * 确认和取消按钮
     */
    public interface OnSelectedListener {
        void onSelected();

        void onCancel();
    }

    public TelDialog setOnSelectedListener(final OnSelectedListener onSelectedListener) {

        tvConfirm.setOnClickListener(new View.OnClickListener() { //呼叫
            @Override
            public void onClick(View v) {
                onSelectedListener.onSelected();
                dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() { //取消
            @Override
            public void onClick(View v) {
                onSelectedListener.onCancel();
                dismiss();
            }
        });
        return this;
    }
}

