package com.pasc.lib.widget.dialognt;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.ScreenUtils;

/**
 * Created by huangkuan on 2017/8/5.
 * 已经废弃掉，改用BottomChoiceDialogFragment
 */
@Deprecated
public class ChooseUserPhotoDialog extends Dialog {
    public static final String TAG = "ChooseUserPhotoDialog";
    private TextView mTvTake;
    private TextView mTvChoose;
    private TextView mTvCancel;

    public ChooseUserPhotoDialog(Context context, int resId) {
        super(context, R.style.style_dialog_select_item);
        setContentView(resId);
        mTvTake = findViewById(R.id.tv_take);
        mTvChoose = findViewById(R.id.tv_get);
        mTvCancel = findViewById(R.id.tv_cancel);

        setLayoutParams(context, resId);
    }

    private void setLayoutParams(Context context, int resId) {
        int widthPixels = ScreenUtils.getScreenWidth();
        View contentView = LayoutInflater.from(context).inflate(resId, null);
        setContentView(contentView);
        mTvTake = findViewById(R.id.tv_take);
        mTvChoose = findViewById(R.id.tv_get);
        mTvCancel = findViewById(R.id.tv_cancel);

        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = widthPixels - DensityUtils.dp2px(12);
        params.bottomMargin = DensityUtils.dp2px(10);
        contentView.setLayoutParams(params);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //        lp.gravity = Gravity.BOTTOM;
        //        lp.height = DensityUtils.dp2px(190);
        //        lp.width = widthPixels;
        //        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    public interface OnSelectedListener {
        void onTake();

        void onChoose();

        void onCancel();
    }

    public void setOnSelectedListener(final OnSelectedListener onSelectedListener) {
        mTvTake.setOnClickListener(new View.OnClickListener() {//确认
            @Override
            public void onClick(View v) {
                if (onSelectedListener != null) {
                    onSelectedListener.onTake();
                }
                dismiss();
            }
        });

        mTvChoose.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                if (onSelectedListener != null) {
                    onSelectedListener.onChoose();
                }
                dismiss();
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectedListener != null) {
                    onSelectedListener.onCancel();
                }
                dismiss();
            }
        });
    }
}
