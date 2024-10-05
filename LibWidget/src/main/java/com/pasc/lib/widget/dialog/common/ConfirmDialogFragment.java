package com.pasc.lib.widget.dialog.common;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmChoiceStateListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;

public class ConfirmDialogFragment extends BaseDialogFragment {
    private ConfirmController mController;
    //底部取消按钮
    private TextView mCancelTv;
    //底部确认按钮
    private TextView mConfirmTv;
    //checkBox提示栏目容器
    private LinearLayout mCheckBoxContainer;
    //右上角删除按钮图标
    private ImageView mCloseIv;
    //checkBox提示栏
    private CheckBox mCheckBox;

    public ConfirmDialogFragment() {
        mController = new ConfirmController();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mController.getAnimationType() == null) {
            setStyle(STYLE_NO_FRAME, R.style.InsetDialog);
        } else {
            if (mController.getAnimationType() == AnimationType.TRANSLATE_BOTTOM) {
                setStyle(STYLE_NO_FRAME, R.style.style_dialog_select_item);
            } else {
                setStyle(STYLE_NO_FRAME, R.style.InsetDialog);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SAVE, mController);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pasc_widget_confirm_dialog, container, false);
        //弹窗icon
        ImageView iconIv = rootView.findViewById(R.id.img_id);
        //弹窗Title
        TextView titleTv = rootView.findViewById(R.id.title);
        //弹窗文字描述
        TextView descTv = rootView.findViewById(R.id.desc);
        //右上角删除按钮容器
        RelativeLayout closeIvContainer = rootView.findViewById(R.id.rel_del_img);
        //右上角删除按钮图标
        mCloseIv = rootView.findViewById(R.id.close);
        //checkBox提示栏目容器
        mCheckBoxContainer = rootView.findViewById(R.id.lin_check);
        //checkBox提示栏
        mCheckBox = rootView.findViewById(R.id.check_id);
        //底部按钮容器
        LinearLayout bottomBtnContainer = rootView.findViewById(R.id.ll_btn);
        //底部取消按钮
        mCancelTv = rootView.findViewById(R.id.cancel_tv);
        //底部确认按钮
        mConfirmTv = rootView.findViewById(R.id.confirm_tv);
        //横向分割线
        View horizonalDivider = rootView.findViewById(R.id.line_view);
        //竖直分割线
        View verticalDivider = rootView.findViewById(R.id.vertical_line);

        if (savedInstanceState != null) {
            //从临时存储中获取缓存信息
            mController = (ConfirmController) savedInstanceState.getSerializable(KEY_SAVE);
        }
        if (mController == null) {
            return rootView;
        }

        //判断当前是否需要设置描述文字行之间的间距
        if (mController.getLineSpacingExtra() != 0) {
            descTv.setLineSpacing(DensityUtils.dp2px(mController.getLineSpacingExtra()), 1);
        }

        //判断当前是否设置了右上角"关闭"图标
        if (mController.getImageDel() != 0) {
            mCloseIv.setImageResource(mController.getImageDel());
            closeIvContainer.setVisibility(View.VISIBLE);
        } else {
            closeIvContainer.setVisibility(View.GONE);
        }

        if (mController.getImageRes() != 0) {
            if (mController.getTitle() != null && mController.getDesc() != null) {
                LinearLayout.LayoutParams layoutTitle = (LinearLayout.LayoutParams) titleTv.getLayoutParams();
                setMargins(layoutTitle, 11);
                descTv.setLayoutParams(layoutTitle);
                LinearLayout.LayoutParams layoutDesc = (LinearLayout.LayoutParams) descTv.getLayoutParams();
                setMargins(layoutDesc, 4);
                descTv.setLayoutParams(layoutDesc);
            }
            if (mController.getTitle() != null && mController.getDesc() == null) {
                LinearLayout.LayoutParams layoutTitle = (LinearLayout.LayoutParams) titleTv.getLayoutParams();
                setMargins(layoutTitle, 11);
                descTv.setLayoutParams(layoutTitle);
            }
            if (mController.getTitle() == null && mController.getDesc() != null) {
                LinearLayout.LayoutParams layoutDesc = (LinearLayout.LayoutParams) descTv.getLayoutParams();
                setMargins(layoutDesc, 11);
                descTv.setLayoutParams(layoutDesc);
            }

        } else {
            if (mController.getTitle() != null && mController.getDesc() != null) {
                LinearLayout.LayoutParams layoutDesc = (LinearLayout.LayoutParams) descTv.getLayoutParams();
                setMargins(layoutDesc, 8);
                descTv.setLayoutParams(layoutDesc);
            }
        }

        if (mController.getImageRes() == 0) {
            iconIv.setVisibility(View.GONE);
        } else {
            iconIv.setImageResource(mController.getImageRes());
        }
        if (mController.getTitle() != null) {
            titleTv.setText(mController.getTitle());
            if (mController.getTitleSize() != 0) {
                titleTv.setTextSize(mController.getTitleSize());
            }
            if (mController.getTitleColor() != 0) {
                titleTv.setTextColor(mController.getTitleColor());
            }
        } else {
            titleTv.setVisibility(View.GONE);
        }

        if (mController.getDesc() != null) {
            descTv.setText(mController.getDesc());
            if (mController.getDescSize() != 0) {
                descTv.setTextSize(mController.getDescSize());
            }
            if (mController.getDescColor() != 0) {
                descTv.setTextColor(mController.getDescColor());
            }
        } else {
            descTv.setVisibility(View.GONE);
        }
        if (mController.isEnableNoLongerAsked()) {
            mCheckBoxContainer.setVisibility(View.VISIBLE);
        } else {
            mCheckBoxContainer.setVisibility(View.GONE);
        }

        if (mController.isHideLeftButton()) {
            verticalDivider.setVisibility(View.GONE);
            mCancelTv.setVisibility(View.GONE);
        }

        if (mController.isHideRightButton()) {
            verticalDivider.setVisibility(View.GONE);
            mConfirmTv.setVisibility(View.GONE);
        }

        if (mController.getConfirmText() != null) {
            mConfirmTv.setText(mController.getConfirmText());

        }
        if (mController.getConfirmTextSize() != 0) {

            mConfirmTv.setTextSize(mController.getConfirmTextSize());
        }
        if (mController.getConfirmTextColor() != 0) {

            mConfirmTv.setTextColor(mController.getConfirmTextColor());
        }
        if (mController.getCloseText() != null) {
            mCancelTv.setText(mController.getCloseText());

        }
        if (mController.getCloseTextSize() != 0) {
            mCancelTv.setTextSize(mController.getCloseTextSize());
        }
        if (mController.getCloseTextColor() != 0) {
            mCancelTv.setTextColor(mController.getCloseTextColor());
        }
        registerListener();
        return rootView;
    }

    private void registerListener() {
        mCheckBoxContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    mCheckBox.setChecked(false);
                } else {
                    mCheckBox.setChecked(true);
                }
            }
        });

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mController.getOnCloseListener() != null) {
                    mController.getOnCloseListener().onClose(ConfirmDialogFragment.this);
                } else {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }
            }
        });
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mController.getOnCloseListener() != null) {
                    mController.getOnCloseListener().onClose(ConfirmDialogFragment.this);
                } else {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }
            }
        });
        mConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mController.isEnableNoLongerAsked()) {
                    boolean isCheckState = mCheckBox.isChecked();
                    if (mController.getOnConfirmChoiceStateListener() != null) {
                        mController.getOnConfirmChoiceStateListener().onConfirm(ConfirmDialogFragment.this, isCheckState);
                    } else {
                        dismiss();
                    }
                } else {
                    if (mController.getOnConfirmListener() != null) {
                        mController.getOnConfirmListener().onConfirm(ConfirmDialogFragment.this);
                    } else {
                        // 如果没有设置监听，则关闭窗口
                        dismiss();
                    }
                }
            }
        });
    }

    private void setMargins(LinearLayout.LayoutParams layoutParams, int marginsTopDp) {
        layoutParams.setMargins(0, DensityUtils.dp2px(marginsTopDp), 0, 0);
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(mController.isCancelable());
        getDialog().setCanceledOnTouchOutside(mController.isCancelable());
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public static class Builder {
        private final ConfirmController.ControllerParams mDialogcontroller;

        public Builder() {
            mDialogcontroller = new ConfirmController.ControllerParams();
        }

        public Builder setCancelable(boolean isCancelable) {
            mDialogcontroller.isCancelable = isCancelable;
            return this;
        }

        public Builder setHideCloseButton(boolean isHide) {
            mDialogcontroller.isHideLeftButton = isHide;
            return this;
        }

        public Builder setHideConfirmButton(boolean isHide) {
            mDialogcontroller.isHideRightButton = isHide;
            return this;
        }


        public Builder setImageRes(@DrawableRes int imageRes) {
            mDialogcontroller.imageRes = imageRes;
            return this;
        }

        public Builder setImageDel(@DrawableRes int imageRes) {
            mDialogcontroller.imageDel = imageRes;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            mDialogcontroller.title = title;
            return this;
        }

        public Builder setTitleSize(int size) {
            mDialogcontroller.titleSize = size;
            return this;
        }

        public Builder setTitleColor(int color) {
            mDialogcontroller.titleColor = color;
            return this;
        }

        public Builder setDesc(CharSequence desc) {
            mDialogcontroller.desc = desc;
            return this;
        }

        public Builder setDescLineSpacingExtra(int spacing) {
            mDialogcontroller.lineSpacingExtra = spacing;
            return this;
        }

        public Builder setDescSize(int descSize) {
            mDialogcontroller.descSize = descSize;
            return this;
        }

        public Builder setDescColor(int descColor) {
            mDialogcontroller.descColor = descColor;
            return this;
        }

        public Builder isEnableNoLongerAsked(boolean isEnable) {
            mDialogcontroller.enableNoLongerAsked = isEnable;
            return this;
        }


        public Builder setConfirmText(CharSequence mConfirmText) {
            mDialogcontroller.confirmText = mConfirmText;
            return this;
        }

        public Builder setConfirmTextSize(int mConfirmTextSize) {
            mDialogcontroller.confirmTextSize = mConfirmTextSize;
            return this;
        }

        public Builder setConfirmTextColor(int mConfirmTextColor) {
            mDialogcontroller.confirmTextColor = mConfirmTextColor;
            return this;
        }

        public Builder setCloseText(CharSequence mCloseText) {
            mDialogcontroller.closeText = mCloseText;
            return this;
        }

        public Builder setCloseTextSize(int negativeTextSize) {
            mDialogcontroller.closeTextSize = negativeTextSize;
            return this;
        }

        public Builder setCloseTextColor(int negativeTextColor) {
            mDialogcontroller.closeTextColor = negativeTextColor;
            return this;
        }

        public Builder setAnimationType(AnimationType animationType) {
            mDialogcontroller.animationType = animationType;
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener<ConfirmDialogFragment> onConfirmListener) {
            mDialogcontroller.onConfirmListener = onConfirmListener;
            return this;
        }

        public Builder setOnConfirmChoiceStateListener(OnConfirmChoiceStateListener<ConfirmDialogFragment> onConfirmChoiceStateListener) {
            mDialogcontroller.onConfirmChoiceStateListener = onConfirmChoiceStateListener;
            return this;
        }

        public Builder setOnCloseListener(OnCloseListener<ConfirmDialogFragment> onCloseListener) {
            mDialogcontroller.onCloseListener = onCloseListener;
            return this;
        }

        public ConfirmDialogFragment build() {
            ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
            mDialogcontroller.apply(confirmDialogFragment.mController);
            confirmDialogFragment.setCancelable(mDialogcontroller.isCancelable);
            return confirmDialogFragment;
        }

        public ConfirmDialogFragment show(FragmentManager manager, String tag) {
            ConfirmDialogFragment customDialog = build();
            customDialog.show(manager, tag);
            return customDialog;
        }
    }

}
