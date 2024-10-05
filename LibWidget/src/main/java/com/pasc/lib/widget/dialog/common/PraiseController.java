package com.pasc.lib.widget.dialog.common;

import com.pasc.lib.widget.dialog.OnButtonClickListener;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;

import java.util.ArrayList;

public class PraiseController {

    private boolean isCancelable;

    //设置对话框图片
    private int imageRes;
    private int imageDel;
    //标题
    private CharSequence title;
    private CharSequence desc;

    private CharSequence primaryButtonText;
    private int primaryButtonTextColor;
    private CharSequence secondaryButtonText;
    private int secondaryButtonTextColor;
    private CharSequence tertiaryButtonText;
    private int tertiaryButtonTextColor;

    private int primaryButtonBackground;
    private int secondaryButtonBackground;
    private int tertiaryButtonBackground;


    private CharSequence closeText;

    private boolean isClosable;
    private boolean isTitleVisible;
    private boolean primaryButtonVisible;
    private boolean secondaryButtonVisible;
    private boolean tertiaryButtonVisible;

    private OnButtonClickListener<PraiseDialogFragment> onPrimaryButtonClickListener;
    private OnButtonClickListener<PraiseDialogFragment> onSecondaryClickListener;
    private OnButtonClickListener<PraiseDialogFragment> onTertiaryClickListener;

    private OnCloseListener<PraiseDialogFragment> onCloseListener;

    private OnSingleChoiceListener<PraiseDialogFragment> onSingleChoiceListener; // 单选监听器



    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public boolean isClosable() {
        return isClosable;
    }

    public void setClosable(boolean closable) {
        isClosable = closable;
    }

    public boolean isTitleVisible() {
        return isTitleVisible;
    }

    public void setTitleVisible(boolean titleVisible) {
        isTitleVisible = titleVisible;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int getImageDel() {
        return imageDel;
    }

    public void setImageDel(int imageDel) {
        this.imageDel = imageDel;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public CharSequence getDesc() {
        return desc;
    }

    public void setDesc(CharSequence desc) {
        this.desc = desc;
    }

    public CharSequence getPrimaryButtonText() {
        return primaryButtonText;
    }

    public void setPrimaryButtonText(CharSequence primaryButtonText) {
        this.primaryButtonText = primaryButtonText;
    }

    public CharSequence getSecondaryButtonText() {
        return secondaryButtonText;
    }

    public void setSecondaryButtonText(CharSequence secondaryButtonText) {
        this.secondaryButtonText = secondaryButtonText;
    }

    public CharSequence getTertiaryButtonText() {
        return tertiaryButtonText;
    }

    public void setTertiaryButtonText(CharSequence tertiaryButtonText) {
        this.tertiaryButtonText = tertiaryButtonText;
    }

    public int getPrimaryButtonBackground() {
        return primaryButtonBackground;
    }

    public void setPrimaryButtonBackground(int primaryButtonBackground) {
        this.primaryButtonBackground = primaryButtonBackground;
    }

    public int getSecondaryButtonBackground() {
        return secondaryButtonBackground;
    }

    public void setSecondaryButtonBackground(int secondaryButtonBackground) {
        this.secondaryButtonBackground = secondaryButtonBackground;
    }

    public int getTertiaryButtonBackground() {
        return tertiaryButtonBackground;
    }

    public void setTertiaryButtonBackground(int tertiaryButtonBackground) {
        this.tertiaryButtonBackground = tertiaryButtonBackground;
    }

    public int getPrimaryButtonTextColor() {
        return primaryButtonTextColor;
    }

    public void setPrimaryButtonTextColor(int primaryButtonTextColor) {
        this.primaryButtonTextColor = primaryButtonTextColor;
    }

    public int getSecondaryButtonTextColor() {
        return secondaryButtonTextColor;
    }

    public void setSecondaryButtonTextColor(int secondaryButtonTextColor) {
        this.secondaryButtonTextColor = secondaryButtonTextColor;
    }

    public int getTertiaryButtonTextColor() {
        return tertiaryButtonTextColor;
    }

    public void setTertiaryButtonTextColor(int tertiaryButtonTextColor) {
        this.tertiaryButtonTextColor = tertiaryButtonTextColor;
    }

    public CharSequence getCloseText() {
        return closeText;
    }

    public void setCloseText(CharSequence closeText) {
        this.closeText = closeText;
    }

    public boolean isPrimaryButtonVisible() {
        return primaryButtonVisible;
    }

    public void setPrimaryButtonVisible(boolean primaryButtonVisible) {
        this.primaryButtonVisible = primaryButtonVisible;
    }

    public boolean isSecondaryButtonVisible() {
        return secondaryButtonVisible;
    }

    public void setSecondaryButtonVisible(boolean secondaryButtonVisible) {
        this.secondaryButtonVisible = secondaryButtonVisible;
    }

    public boolean isTertiaryButtonVisible() {
        return tertiaryButtonVisible;
    }

    public void setTertiaryButtonVisible(boolean tertiaryButtonVisible) {
        this.tertiaryButtonVisible = tertiaryButtonVisible;
    }

    public OnButtonClickListener<PraiseDialogFragment> getOnPrimaryButtonClickListener() {
        return onPrimaryButtonClickListener;
    }

    public void setOnPrimaryButtonClickListener(OnButtonClickListener<PraiseDialogFragment> onPrimaryButtonClickListener) {
        this.onPrimaryButtonClickListener = onPrimaryButtonClickListener;
    }

    public OnButtonClickListener<PraiseDialogFragment> getOnSecondaryClickListener() {
        return onSecondaryClickListener;
    }

    public void setOnSecondaryClickListener(OnButtonClickListener<PraiseDialogFragment> onSecondaryClickListener) {
        this.onSecondaryClickListener = onSecondaryClickListener;
    }

    public OnButtonClickListener<PraiseDialogFragment> getOnTertiaryClickListener() {
        return onTertiaryClickListener;
    }

    public void setOnTertiaryClickListener(OnButtonClickListener<PraiseDialogFragment> onTertiaryClickListener) {
        this.onTertiaryClickListener = onTertiaryClickListener;
    }

    public OnCloseListener<PraiseDialogFragment> getOnCloseListener() {
        return onCloseListener;
    }

    public void setOnCloseListener(OnCloseListener<PraiseDialogFragment> onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    public OnSingleChoiceListener<PraiseDialogFragment> getOnSingleChoiceListener() {
        return onSingleChoiceListener;
    }

    public void setOnSingleChoiceListener(OnSingleChoiceListener<PraiseDialogFragment> onSingleChoiceListener) {
        this.onSingleChoiceListener = onSingleChoiceListener;
    }

    public static class ControllerParams{

        public boolean isCancelable;
        //设置对话框图片
        public int imageRes;

        public int imageDel;
        //标题
        public CharSequence title;

        public CharSequence desc;

        public CharSequence primaryButtonText;
        public CharSequence secondaryButtonText;
        public CharSequence tertiaryButtonText;

        public int primaryButtonBackground;
        public int secondaryButtonBackground;
        public int tertiaryButtonBackground;

        public int primaryButtonTextColor;
        public int secondaryButtonTextColor;
        public int tertiaryButtonTextColor;

        public CharSequence closeText;

        public boolean isClosable = false;
        public boolean isTitleVisible = true;

        public boolean primaryButtonVisible = true;
        public boolean secondaryButtonVisible = true;
        public boolean tertiaryButtonVisible = true;

        public ArrayList<CharSequence> items;

        public OnButtonClickListener<PraiseDialogFragment> onPrimaryButtonClickListener;
        public OnButtonClickListener<PraiseDialogFragment> onSecondaryClickListener;
        public OnButtonClickListener<PraiseDialogFragment> onTertiaryClickListener;

        public OnCloseListener<PraiseDialogFragment> onCloseListener;

        public OnSingleChoiceListener<PraiseDialogFragment> onSingleChoiceListener; // 单选监听器

        public void apply(PraiseController controller) {

            controller.setCancelable(isCancelable);

            controller.setImageRes(imageRes);
            controller.setImageDel(imageDel);
            controller.setTitle(title);
            controller.setDesc(desc);
            controller.setClosable(isClosable);
            controller.setTitleVisible(isTitleVisible);
            controller.setPrimaryButtonText(primaryButtonText);
            controller.setPrimaryButtonTextColor(primaryButtonTextColor);
            controller.setSecondaryButtonText(secondaryButtonText);
            controller.setSecondaryButtonTextColor(secondaryButtonTextColor);
            controller.setTertiaryButtonText(tertiaryButtonText);
            controller.setTertiaryButtonTextColor(tertiaryButtonTextColor);

            controller.setPrimaryButtonBackground(primaryButtonBackground);
            controller.setSecondaryButtonBackground(secondaryButtonBackground);
            controller.setTertiaryButtonBackground(tertiaryButtonBackground);
            controller.setPrimaryButtonVisible(primaryButtonVisible);
            controller.setSecondaryButtonVisible(secondaryButtonVisible);
            controller.setTertiaryButtonVisible(tertiaryButtonVisible);

            controller.setCloseText(closeText);
            controller.setOnPrimaryButtonClickListener(onPrimaryButtonClickListener);
            controller.setOnSecondaryClickListener(onSecondaryClickListener);
            controller.setOnTertiaryClickListener(onTertiaryClickListener);
            controller.setOnCloseListener(onCloseListener);
            controller.setOnSingleChoiceListener(onSingleChoiceListener);
        }
    }
}
