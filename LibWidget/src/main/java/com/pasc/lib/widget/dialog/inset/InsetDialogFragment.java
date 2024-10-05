package com.pasc.lib.widget.dialog.inset;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;

/**
 * Created by chenruihan410 on 2018/10/10.
 */
public class InsetDialogFragment extends BaseDialogFragment {

    private static final String ARG_IMAGE_RES = "imageRes";
    private static final String ARG_DESC = "desc";
    private static final String ARG_CONFIRM_TEXT = "confirmText";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.InsetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_inset, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle arguments = getArguments();

        ImageView img = view.findViewById(R.id.img);
        if (arguments != null) {
            int imageRes = arguments.getInt(ARG_IMAGE_RES, R.drawable.bg_inset_dialog_header);
            img.setImageResource(imageRes);
        }

        Button confirm = view.findViewById(R.id.confirm);
        if (arguments != null) {
            CharSequence confirmText = arguments.getCharSequence(ARG_CONFIRM_TEXT);
            if (!TextUtils.isEmpty(confirmText)) {
                confirm.setText(confirmText);
            }
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendMessage(ARG_ON_CONFIRM_LISTENER)) {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }
            }
        });

        View close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendMessage(ARG_ON_CLOSE_LISTENER)) {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }
            }
        });

        TextView desc = view.findViewById(R.id.desc);
        desc.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (arguments != null) {
            desc.setText(arguments.getCharSequence(ARG_DESC));
        }
    }

    public static class Builder {

        int imageRes = R.drawable.bg_inset_dialog_header; // 图片资源
        CharSequence desc; // 描述
        CharSequence confirmText; // 确认文本
        OnConfirmListener onConfirmListener; // 确认回调
        OnCloseListener onCloseListener; // 关闭回调

        public Builder setImageRes(int imageRes) {
            this.imageRes = imageRes;
            return this;
        }

        public Builder setDesc(CharSequence desc) {
            this.desc = desc;
            return this;
        }

        public Builder setConfirmText(CharSequence confirmText) {
            this.confirmText = confirmText;
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }

        public Builder setOnCloseListener(OnCloseListener onCloseListener) {
            this.onCloseListener = onCloseListener;
            return this;
        }

        public InsetDialogFragment build() {
            InsetDialogFragment dialogFragment = new InsetDialogFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_IMAGE_RES, imageRes);
            if (!TextUtils.isEmpty(desc)) {
                args.putCharSequence(ARG_DESC, desc);
            }
            if (!TextUtils.isEmpty(confirmText)) {
                args.putCharSequence(ARG_CONFIRM_TEXT, confirmText);
            }
            if (onConfirmListener != null) {
                args.putParcelable(ARG_ON_CONFIRM_LISTENER, dialogFragment.obtainMessage(WHAT_ON_CONFIRM_LISTENER, onConfirmListener));
            }
            if (onCloseListener != null) {
                args.putParcelable(ARG_ON_CLOSE_LISTENER, dialogFragment.obtainMessage(WHAT_ON_CLOSE_LISTENER, onCloseListener));
            }

            dialogFragment.setArguments(args);
            dialogFragment.setCancelable(false);
            return dialogFragment;
        }
    }
}
