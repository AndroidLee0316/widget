package com.pasc.lib.widget.dialog.common;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.DialogFragmentInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/3/29
 * <p>
 * 权限询问弹窗，用于替换{@link PraiseDialogFragment}好评弹窗 和 {@link PermissionDialogFragment}权限弹窗
 */
public class PermissionDialogFragment2 extends BaseDialogFragment {
    private PermissionController2 mController;
    private List<View> mButtons = new ArrayList<>();

    public PermissionDialogFragment2() {
        mController = new PermissionController2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.InsetDialog);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SAVE, mController);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pasc_permission_dialog_fragment2, container, false);
        LinearLayout btnContainerLl = rootView.findViewById(R.id.btn_container);
        ImageView iconIv = rootView.findViewById(R.id.icon);
        TextView titleTv = rootView.findViewById(R.id.title);
        TextView descTv = rootView.findViewById(R.id.desc);
        ImageView closeIv = rootView.findViewById(R.id.close);
        if (savedInstanceState != null) {
            mController = (PermissionController2) savedInstanceState.getSerializable(KEY_SAVE);
        }
        if (mController == null) {
            return rootView;
        }

        if (!mController.isCloseImgVisible()) {
            //当前不需要右上角"关闭"按钮
            closeIv.setVisibility(View.GONE);
        }
        if (mController.getIconResId() == 0) {
            //当前不需要显示icon
            iconIv.setVisibility(View.GONE);
        } else {
            //展示外部设置的icon
            iconIv.setImageResource(mController.getIconResId());
        }
        //估算desc文字描述在弹窗中的位置
        computeDescViewPos(descTv);
        if (mController.getTitle() != null) {
            //当前标题内容不为空
            titleTv.setText(mController.getTitle());
        } else {
            titleTv.setVisibility(View.GONE);
        }
        if (mController.getDesc() != null) {
            //当前弹窗文字描述不为空
            descTv.setText(mController.getDesc());
        } else {
            descTv.setVisibility(View.GONE);
        }
        if (mController.getButtonWrapper().getItems().isEmpty()) {
            btnContainerLl.setVisibility(View.GONE);
        } else {
            //显示弹窗按钮
            addButtonView(inflater, btnContainerLl, mController.getButtonWrapper());
        }

        int childCount = btnContainerLl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childAt = btnContainerLl.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            if (i == childCount - 1) {
                //最后一个button
                layoutParams.bottomMargin = 0;
            } else {
                layoutParams.bottomMargin = DensityUtils.dip2px(getContext(), 12);
            }
            childAt.setLayoutParams(layoutParams);
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mController.onClickListener != null) {
                        mController.onClickListener.onClick(PermissionDialogFragment2.this, mButtons.indexOf(childAt));
                    } else {
                        dismiss();
                    }
                }
            });
        }
        setListener(closeIv);
        return rootView;
    }

    private void setListener(ImageView closeIv) {
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mController.onCancelListener != null) {
                    mController.onCancelListener.onCancel(PermissionDialogFragment2.this);
                }
                dismiss();
            }
        });
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mController.onDismissListener != null) {
                    mController.onDismissListener.onDismiss(PermissionDialogFragment2.this);
                }
            }
        });
    }

    /**
     * 添加弹窗按钮
     *
     * @param buttonWrapper button
     */
    private void addButtonView(LayoutInflater inflater, LinearLayout btnContainer, ButtonWrapper buttonWrapper) {
        for (int i = 0; i < buttonWrapper.getItems().size(); i++) {
            View view = inflater.inflate(R.layout.pasc_item_button_permission2, btnContainer, false);
            Button button = view.findViewById(R.id.btn);
            button.setText(buttonWrapper.getItems().get(i));
            button.setBackgroundResource(buttonWrapper.getButtonBgs().get(i));
            button.setTextColor(getResources().getColorStateList(buttonWrapper.getTextColors().get(i)));
            btnContainer.addView(view);
            mButtons.add(button);
        }
    }

    private void computeDescViewPos(TextView descTv) {
        if (mController.getTitle() != null && mController.getDesc() != null) {
            //有标题 && 有描述
            return;
        }
        if (mController.getTitle() != null && mController.getDesc() == null) {
            //只有标题
            descTv.setVisibility(View.GONE);
            return;
        }
        if (mController.getTitle() == null && mController.getDesc() != null) {
            //只有描述
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) descTv.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            descTv.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(mController.isClosable());
    }

    public static class Builder {
        private final PermissionController2.ControllerParams controllerParams;

        public Builder() {
            controllerParams = new PermissionController2.ControllerParams();
        }

        /**
         * 设置关闭按钮的显示与隐藏状态
         *
         * @param closeImgVisible true:显示，false：隐藏
         */
        public Builder setCloseImgVisible(boolean closeImgVisible) {
            controllerParams.isCloseImgVisible = closeImgVisible;
            return this;
        }


        /**
         * 设置弹窗是否允许点击蒙层或者手机虚拟返回键关闭
         *
         * @param closable true:允许，false：不允许
         */
        public Builder setClosable(boolean closable) {
            controllerParams.isClosable = closable;
            return this;
        }

        /**
         * 设置icon图标资源
         *
         * @param iconResId icon图标资源id
         */
        public Builder setIconResId(@DrawableRes int iconResId) {
            controllerParams.iconResId = iconResId;
            return this;
        }

        /**
         * 设置关闭图标资源
         *
         * @param closeResId icon图标资源id
         */
        public Builder setCloseResId(@DrawableRes int closeResId) {
            controllerParams.closeResId = closeResId;
            return this;
        }

        /**
         * 设置弹窗的标题。若没有设置，则认定为没有标题
         *
         * @param title 标题字符串
         */
        public Builder setTitle(CharSequence title) {
            controllerParams.title = title;
            return this;
        }

        /**
         * 设置弹窗的文字描述
         *
         * @param desc 文字描述
         */
        public Builder setDesc(CharSequence desc) {
            controllerParams.desc = desc;
            return this;
        }

        /**
         * 设置多个button
         *
         * @param wrapper         button的文字
         * @param onClickListener 按钮点击监听
         */
        public Builder setButton(ButtonWrapper wrapper, DialogFragmentInterface.OnClickListener<PermissionDialogFragment2> onClickListener) {
            controllerParams.buttonWrapper = wrapper;
            controllerParams.onClickListener = onClickListener;
            return this;
        }

        /**
         * 设置关闭按钮点击
         *
         * @param onCancelListener 右上角关闭按钮点击回调监听
         */
        public Builder setOnCancelListener(DialogFragmentInterface.OnCancelListener<PermissionDialogFragment2> onCancelListener) {
            controllerParams.onCancelListener = onCancelListener;
            return this;
        }

        /**
         * 弹窗dismiss时回调
         *
         * @param onDismissListener 回调
         */
        public Builder setOnDismissListener(DialogFragmentInterface.OnDismissListener<PermissionDialogFragment2> onDismissListener) {
            controllerParams.onDismissListener = onDismissListener;
            return this;
        }

        public PermissionDialogFragment2 build() {
            PermissionDialogFragment2 permissionDialogFragment = new PermissionDialogFragment2();
            controllerParams.apply(permissionDialogFragment.mController);
            return permissionDialogFragment;
        }

        public PermissionDialogFragment2 show(FragmentManager manager, String tag) {
            PermissionDialogFragment2 customDialog = build();
            customDialog.show(manager, tag);
            return customDialog;
        }
    }

}
