package com.pasc.lib.widget.dialognt;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.ScreenUtils;
/**
 * 已经废弃掉，改用BottomChoiceDialogFragment
 */
@Deprecated
public class BottomConfirmDialog extends Dialog {
    TextView tvConfirm;
    TextView tvCancel;

    public BottomConfirmDialog(@NonNull Context context) {
        super(context, R.style.style_dialog_select_item);
        int widthPixels = ScreenUtils.getScreenWidth();
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_confirm, null);
        setContentView(contentView);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvCancel = findViewById(R.id.tv_cancel);
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = widthPixels - DensityUtils.dp2px(12);
        params.bottomMargin = DensityUtils.dp2px(10);
        contentView.setLayoutParams(params);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    /**
     * 确认和取消按钮
     */
    public interface OnSelectedListener {
        void onSelected();

        void onCancel();
    }

    public BottomConfirmDialog setOnSelectedListener(final OnSelectedListener onSelectedListener) {

        tvConfirm.setOnClickListener(new View.OnClickListener() {//确认
            @Override
            public void onClick(View v) {
                onSelectedListener.onSelected();
                dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                onSelectedListener.onCancel();
                dismiss();
            }
        });
        return this;
    }
}
