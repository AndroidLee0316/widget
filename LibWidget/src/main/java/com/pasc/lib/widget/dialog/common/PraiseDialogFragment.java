package com.pasc.lib.widget.dialog.common;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnButtonClickListener;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;

import java.lang.ref.WeakReference;

public class PraiseDialogFragment extends BaseDialogFragment {
    private static final int DIALOG_DISSMISS = 0;
    private View mView;
    final PraiseController controller;
    private RelativeLayout mRelCloseImg;
    public PraiseDialogFragment(){
        controller = new PraiseController();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.InsetDialog);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

            mView = inflater.inflate(R.layout.praise_dialog_fragment,container,false);
            Button mPrimaryButton = mView.findViewById(R.id.primary_button);
            Button mSecondaryButton = mView.findViewById(R.id.secondary_button);
            Button mTertiaryButton = mView.findViewById(R.id.tertiary_button);

            ImageView imgRes= mView.findViewById(R.id.img_id);
            mRelCloseImg = mView.findViewById(R.id.rel_close_img);
            TextView title = mView.findViewById(R.id.title);

            TextView desc = mView.findViewById(R.id.desc);

            ImageView close = mView.findViewById(R.id.close);

            if(!controller.isClosable()){
                mRelCloseImg.setVisibility(View.GONE);
            }

            if(controller.getImageRes() != 0){
                if(controller.getTitle() != null && controller.getDesc() !=null ){

                    LinearLayout.LayoutParams layoutTitle=(LinearLayout.LayoutParams)title.getLayoutParams();
                    setMargins(layoutTitle,11);
                    desc.setLayoutParams(layoutTitle);
                    LinearLayout.LayoutParams layoutDesc=(LinearLayout.LayoutParams)desc.getLayoutParams();
                    setMargins(layoutDesc,4);
                    desc.setLayoutParams(layoutDesc);


                }
                if(controller.getTitle() != null && controller.getDesc() ==null ){

                    LinearLayout.LayoutParams layoutTitle=(LinearLayout.LayoutParams)title.getLayoutParams();
                    setMargins(layoutTitle,11);
                    desc.setLayoutParams(layoutTitle);


                }
                if(controller.getTitle() == null && controller.getDesc() !=null ){

                    LinearLayout.LayoutParams layoutDesc=(LinearLayout.LayoutParams)desc.getLayoutParams();
                    setMargins(layoutDesc,11);
                    desc.setLayoutParams(layoutDesc);


                }

            }else {
                if(controller.getTitle() != null && controller.getDesc() !=null ){
                 LinearLayout.LayoutParams layoutDesc=(LinearLayout.LayoutParams)desc.getLayoutParams();
                setMargins(layoutDesc,8);
                desc.setLayoutParams(layoutDesc);

                }

            }

        if(controller.getImageRes() == 0){
            imgRes.setVisibility(View.GONE);
        }else {
            imgRes.setImageResource(controller.getImageRes());
        }
        if(controller.getTitle() != null){
            title.setText(controller.getTitle());

        }else {
            title.setVisibility(View.GONE);
        }
        if(!controller.isTitleVisible()){
            title.setVisibility(View.GONE);
        }

        if(controller.getDesc() !=null ){

            desc.setText(controller.getDesc());

        }else {
            desc.setVisibility(View.GONE);
        }


        if(controller.getPrimaryButtonText() != null){
            mPrimaryButton.setText(controller.getPrimaryButtonText());

          }
          if(controller.getPrimaryButtonBackground() != 0){
              mPrimaryButton.setBackgroundResource(controller.getPrimaryButtonBackground());
          }
          if(controller.getPrimaryButtonTextColor() !=0 ){
              mPrimaryButton.setTextColor(controller.getPrimaryButtonTextColor());
          }

        if(controller.getSecondaryButtonText() != null){
            mSecondaryButton.setText(controller.getSecondaryButtonText());

        }
        if(controller.getSecondaryButtonTextColor() !=0 ){
            mSecondaryButton.setTextColor(controller.getSecondaryButtonTextColor());
        }
        if(controller.getSecondaryButtonBackground() != 0){
            mSecondaryButton.setBackgroundResource(controller.getSecondaryButtonBackground());
        }

        if(controller.getTertiaryButtonText() != null){
            mTertiaryButton.setText(controller.getTertiaryButtonText());

        }
        if(controller.getTertiaryButtonBackground() != 0){
            mTertiaryButton.setBackgroundResource(controller.getTertiaryButtonBackground());
        }
        if(controller.getTertiaryButtonTextColor() !=0 ){
            mTertiaryButton.setTextColor(controller.getTertiaryButtonTextColor());
        }
        if(controller.isPrimaryButtonVisible() == true){
            mPrimaryButton.setVisibility(View.VISIBLE);
        }else{
            mPrimaryButton.setVisibility(View.GONE);
        }
        if(controller.isSecondaryButtonVisible() == true){
            mSecondaryButton.setVisibility(View.VISIBLE);
        }else{
            mSecondaryButton.setVisibility(View.GONE);
        }
        if(controller.isTertiaryButtonVisible() == true){
            mTertiaryButton.setVisibility(View.VISIBLE);
        }else{
            mTertiaryButton.setVisibility(View.GONE);
        }
        if(!controller.isPrimaryButtonVisible()){

             if(controller.isSecondaryButtonVisible() && controller.isTertiaryButtonVisible()){

                 LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)mSecondaryButton.getLayoutParams();
                 setMarginsBottom(layoutParams,27);
                 mSecondaryButton.setLayoutParams(layoutParams);
             }else {
                 if(controller.isSecondaryButtonVisible() && !controller.isTertiaryButtonVisible()){
                     LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)mSecondaryButton.getLayoutParams();
                     setMarginsBottom(layoutParams,27);
                     mSecondaryButton.setLayoutParams(layoutParams);
                 }else if(!controller.isSecondaryButtonVisible() && controller.isTertiaryButtonVisible()){
                     LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)mTertiaryButton.getLayoutParams();
                     setMarginsBottom(layoutParams,27);
                     mTertiaryButton.setLayoutParams(layoutParams);
                 }
             }

        }
        final Bundle arguments = getArguments();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasListener = false;
                if (arguments != null) {
                    Parcelable parcelable = arguments.getParcelable(ARG_ON_CLOSE_LISTENER);
                    if (parcelable != null && parcelable instanceof Message) {
                        Message.obtain(((Message) parcelable)).sendToTarget();
                        hasListener = true;
                    }
                }
                if (!hasListener) {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }
            }
        });
        mPrimaryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                       boolean hasListener = false;
                       if (arguments != null) {
                           Parcelable parcelable = arguments.getParcelable(ARG_ON_PRIMARY_BUTTON_LISTENER);
                           if (parcelable != null && parcelable instanceof Message) {
                               Message.obtain(((Message) parcelable)).sendToTarget();
                               hasListener = true;
                           }
                       }
                       if (!hasListener) {
                           // 如果没有设置监听，则关闭窗口
                           dismiss();
                       }
                }
            });

        mSecondaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasListener = false;
                if (arguments != null) {
                    Parcelable parcelable = arguments.getParcelable(ARG_ON_SECONDARY_BUTTON_LISTENER);
                    if (parcelable != null && parcelable instanceof Message) {
                        Message.obtain(((Message) parcelable)).sendToTarget();
                        hasListener = true;
                    }
                }
                if (!hasListener) {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }
            }
        });

        mTertiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasListener = false;
                if (arguments != null) {
                    Parcelable parcelable = arguments.getParcelable(ARG_ON_TERTIARY_BUTTON_LISTENER);
                    if (parcelable != null && parcelable instanceof Message) {
                        Message.obtain(((Message) parcelable)).sendToTarget();
                        hasListener = true;
                    }
                }
                if (!hasListener) {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }
            }
        });
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return mView;
    }


    private void setMarginsBottom(LinearLayout.LayoutParams layoutParams,int marginsBottomDp){
        layoutParams.setMargins(DensityUtils.dp2px(20),0,DensityUtils.dp2px(20),DensityUtils.dp2px(marginsBottomDp));
    }
    private void setMargins(LinearLayout.LayoutParams layoutParams,int marginsTopDp){
        layoutParams.setMargins(0, DensityUtils.dp2px(marginsTopDp),0,0);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(controller.isCancelable());

    }

    public interface OnBindViewListener{
        void getBindView(View view);
    }

    @Override
    public void onPause() {
        super.onPause();
//        mHandler.removeCallbacksAndMessages(null);
    }

    public static class MyHandler extends Handler {
        private WeakReference<Dialog> mDialog;
        public MyHandler(Dialog dialog){
            mDialog = new WeakReference<Dialog>(dialog);
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DIALOG_DISSMISS:
                    mDialog.get().dismiss();
            }
        }
    }

    public static class Builder{
        public final PraiseController.ControllerParams mDialogcontroller;

        public Builder(){
            mDialogcontroller = new PraiseController.ControllerParams();
        }

        public Builder setCancelable(boolean isCancelable){
            mDialogcontroller.isCancelable = isCancelable;
            return this;
        }

        public Builder setImageRes(@DrawableRes int imageRes) {
            mDialogcontroller.imageRes = imageRes;
            return this;
        }

        public Builder setTitle(CharSequence title){
            mDialogcontroller.title=title;
            return this;
        }

        public Builder setDesc(CharSequence desc) {
            mDialogcontroller.desc = desc;
            return this;
        }

        public Builder setClosable(boolean isClosable) {
            mDialogcontroller.isClosable = isClosable;
            return this;
        }

        public Builder setTitleVisible(boolean isTitleVisible) {
            mDialogcontroller.isTitleVisible = isTitleVisible;
            return this;
        }

        public Builder setPrimaryButtonText(CharSequence mButtonText) {
            mDialogcontroller.primaryButtonText = mButtonText;
            return this;
        }
        public Builder setPrimaryButtonBackground(int drawableRes) {
            mDialogcontroller.primaryButtonBackground = drawableRes;
            return this;
        }
        public Builder setPrimaryButtonTextColor(int color) {
            mDialogcontroller.primaryButtonTextColor = color;
            return this;
        }
        public Builder setSecondaryButtonText(CharSequence mButtonText) {
            mDialogcontroller.secondaryButtonText = mButtonText;
            return this;
        }
        public Builder setSecondaryButtonTextColor(int color) {
            mDialogcontroller.secondaryButtonTextColor = color;
            return this;
        }
        public Builder setSecondaryButtonBackground(int drawableRes) {
            mDialogcontroller.secondaryButtonBackground = drawableRes;
            return this;
        }
        public Builder setTertiaryButtonText(CharSequence mButtonText) {
            mDialogcontroller.tertiaryButtonText = mButtonText;
            return this;
        }
        public Builder setTertiaryButtonBackground(int drawableRes) {
            mDialogcontroller.tertiaryButtonBackground = drawableRes;
            return this;
        }
        public Builder setTertiaryButtonTextColor(int color) {
            mDialogcontroller.tertiaryButtonTextColor = color;
            return this;
        }
        public Builder setPrimaryButtonVisible(boolean isVisible) {
            mDialogcontroller.primaryButtonVisible = isVisible;
            return this;
        }
        public Builder setSecondaryButtonVisible(boolean isVisible) {
            mDialogcontroller.secondaryButtonVisible = isVisible;
            return this;
        }
        public Builder setTertiaryButtonVisible(boolean isVisible) {
            mDialogcontroller.tertiaryButtonVisible = isVisible;
            return this;
        }

        public Builder setPrimaryButtonOnClickListener(OnButtonClickListener<PraiseDialogFragment> onButtonClickListener) {
            mDialogcontroller.onPrimaryButtonClickListener = onButtonClickListener;
            return this;
        }
        public Builder setSecondaryButtonOnClickListener(OnButtonClickListener<PraiseDialogFragment> onButtonClickListener) {
            mDialogcontroller.onSecondaryClickListener = onButtonClickListener;
            return this;
        }
        public Builder setTertiaryButtonOnClickListener(OnButtonClickListener<PraiseDialogFragment> onButtonClickListener) {
            mDialogcontroller.onTertiaryClickListener = onButtonClickListener;
            return this;
        }

        public Builder setOnCloseListener(OnCloseListener<PraiseDialogFragment> onCloseListener) {
            mDialogcontroller.onCloseListener = onCloseListener;
            return this;
        }

        public PraiseDialogFragment build(){
            PraiseDialogFragment praiseDialogFragment = new PraiseDialogFragment();
            mDialogcontroller.apply(praiseDialogFragment.controller);
            Bundle args = new Bundle();

            if (mDialogcontroller.onPrimaryButtonClickListener != null) {
                args.putParcelable(ARG_ON_PRIMARY_BUTTON_LISTENER, praiseDialogFragment.obtainMessage(WHAT_ON_PRIMARY_BUTTON_LISTENER, mDialogcontroller.onPrimaryButtonClickListener));
            }
            if (mDialogcontroller.onSecondaryClickListener != null) {
                args.putParcelable(ARG_ON_SECONDARY_BUTTON_LISTENER, praiseDialogFragment.obtainMessage(WHAT_ON_SECONDARY_BUTTON_LISTENER, mDialogcontroller.onSecondaryClickListener));
            }
            if (mDialogcontroller.onTertiaryClickListener != null) {
                args.putParcelable(ARG_ON_TERTIARY_BUTTON_LISTENER, praiseDialogFragment.obtainMessage(WHAT_ON_TERTIARY_BUTTON_LISTENER, mDialogcontroller.onTertiaryClickListener));
            }

            if (mDialogcontroller.onCloseListener != null) {
                args.putParcelable(ARG_ON_CLOSE_LISTENER, praiseDialogFragment.obtainMessage(WHAT_ON_CLOSE_LISTENER, mDialogcontroller.onCloseListener));
            }
            if (mDialogcontroller.onSingleChoiceListener != null) {
                args.putParcelable(ARG_ON_SINGLE_CHOICE_LISTENER, praiseDialogFragment.obtainMessage(WHAT_ON_SINGLE_CHOICE_LISTENER, mDialogcontroller.onSingleChoiceListener));
            }

            praiseDialogFragment.setArguments(args);

            praiseDialogFragment.setCancelable(mDialogcontroller.isCancelable);
            return praiseDialogFragment;
        }
        public PraiseDialogFragment show(FragmentManager manager, String tag){
            PraiseDialogFragment customDialog = build();
            customDialog.show(manager,tag);
            return customDialog;
        }
    }

    public FragmentManager fragmentManager(){
        FragmentManager fragmentManager = getFragmentManager();
        return fragmentManager;
    }

}
