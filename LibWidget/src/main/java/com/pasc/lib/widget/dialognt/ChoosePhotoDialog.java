package com.pasc.lib.widget.dialognt;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.ScreenUtils;

/**
 * Created by huangkuan on 2017/8/5.
 * 已经废弃掉，改用BottomChoiceDialogFragment
 */
@Deprecated
public class ChoosePhotoDialog extends Dialog {
    public static final String TAG = "ChoosePhotoDialog";
    private TextView mTvTake;
    private TextView mTvChoose;
    private TextView mTvCancel;

    public ChoosePhotoDialog(Context context) {
        super(context, R.style.style_dialog_select_item);
        setContentView(R.layout.dialog_choose_photo);
        mTvTake = findViewById(R.id.tv_take);
        mTvChoose = findViewById(R.id.tv_get);
        mTvCancel = findViewById(R.id.tv_cancel);

        setLayoutParams();
    }


    private void setLayoutParams() {
        int widthPixels = ScreenUtils.getScreenWidth();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lp.gravity = Gravity.BOTTOM;
        lp.height = DensityUtils.dp2px(161);
        lp.width = widthPixels;
        getWindow().setAttributes(lp);
    }

    public interface OnSelectedListener {
        void onTake();

        void onChoose();
    }

    public void setOnSelectedListener(final OnSelectedListener onSelectedListener) {
        mTvTake.setOnClickListener(new View.OnClickListener() {//确认
            @Override
            public void onClick(View v) {
                onSelectedListener.onTake();
                dismiss();
            }
        });

        mTvChoose.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                onSelectedListener.onChoose();
                dismiss();
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
