package com.pasc.lib.widget.dialog.common;

import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnMultiChoiceListener;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChoiceController implements Serializable {
    private boolean isCancelable;
    private CharSequence title;
    private int titleSize;
    private int titleColor;

    private int itemTextColor;
    private int itemTextSize;

    private CharSequence buttonText;
    private int  buttonColor;
    private int  buttonSize;


    private int currentSelectPosition;

    private List<Integer> currentSelectPostionList;


    private ArrayList<CharSequence> items;

    private boolean mIsMultiChoice;
    private boolean mIsSingleChoice;

    private OnSingleChoiceListener<ChoiceDialogFragment> onSingleChoiceListener; // 单选监听器

    private OnMultiChoiceListener<ChoiceDialogFragment> onMultiChoiceListener; // 多选监听器


    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public CharSequence getTitle() {
        return title;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getItemTextColor() {
        return itemTextColor;
    }

    public void setItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
    }

    public int getItemTextSize() {
        return itemTextSize;
    }

    public void setItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
    }

    public CharSequence getButtonText() {
        return buttonText;
    }

    public void setButtonText(CharSequence buttonText) {
        this.buttonText = buttonText;
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }

    public int getButtonSize() {
        return buttonSize;
    }

    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    public int getCurrentSelectPosition() {
        return currentSelectPosition;
    }

    public void setCurrentSelectPosition(int currentSelectPosition) {
        this.currentSelectPosition = currentSelectPosition;
    }

    public List<Integer> getCurrentSelectPostionList() {
        return currentSelectPostionList;
    }

    public void setCurrentSelectPostionList(List<Integer> currentSelectPostionList) {
        this.currentSelectPostionList = currentSelectPostionList;
    }

    public ArrayList<CharSequence> getItems() {
        return items;
    }

    public void setItems(ArrayList<CharSequence> items) {
        this.items = items;
    }

    public boolean ismIsMultiChoice() {
        return mIsMultiChoice;
    }

    public void setmIsMultiChoice(boolean mIsMultiChoice) {
        this.mIsMultiChoice = mIsMultiChoice;
    }

    public boolean ismIsSingleChoice() {
        return mIsSingleChoice;
    }

    public void setmIsSingleChoice(boolean mIsSingleChoice) {
        this.mIsSingleChoice = mIsSingleChoice;
    }

    public OnSingleChoiceListener<ChoiceDialogFragment> getOnSingleChoiceListener() {
        return onSingleChoiceListener;
    }

    public void setOnSingleChoiceListener(OnSingleChoiceListener<ChoiceDialogFragment> onSingleChoiceListener) {
        this.onSingleChoiceListener = onSingleChoiceListener;
    }

    public OnMultiChoiceListener<ChoiceDialogFragment> getOnMultiChoiceListener() {
        return onMultiChoiceListener;
    }

    public void setOnMultiChoiceListener(OnMultiChoiceListener<ChoiceDialogFragment> onMultiChoiceListener) {
        this.onMultiChoiceListener = onMultiChoiceListener;
    }
    //    public OnConfirmListener<ChoiceDialogFragment> getOnBtnClickListener() {
//        return onConfirmListener;
//    }
//
//    public void setOnBtnClickListener(OnConfirmListener<ChoiceDialogFragment> onConfirmListener) {
//        this.onConfirmListener = onConfirmListener;
//    }

    public static class ControllerParams{

        public boolean isCancelable;

        public CharSequence title;
        public int titleColor;
        public int titleSize;

        public int itemTextColor;
        public int itemTextSize;

        public CharSequence buttonText;
        public int  buttonColor;
        public int  buttonSize;

        public int currentSelectPosition;
        public List<Integer> currentSelectPostionList;
        public ArrayList<CharSequence> items;

        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;


        public OnConfirmListener<ChoiceDialogFragment> onConfirmListener;
        public OnSingleChoiceListener<ChoiceDialogFragment> onSingleChoiceListener;

        public OnMultiChoiceListener<ChoiceDialogFragment> onMultiChoiceListener; // 多选监听器

        public void apply(ChoiceController controller) {

            controller.setCancelable(isCancelable);
            //标题
            controller.setTitle(title);
            controller.setTitleColor(titleColor);
            controller.setTitleSize(titleSize);

            //列表itm
            controller.setItemTextColor(itemTextColor);
            controller.setItemTextSize(itemTextSize);

           //确定button
            controller.setButtonText(buttonText);
            controller.setButtonColor(buttonColor);
            controller.setButtonSize(buttonSize);


            controller.setCurrentSelectPosition(currentSelectPosition);
            controller.setCurrentSelectPostionList(currentSelectPostionList);

            controller.setItems(items);
            controller.setmIsSingleChoice(mIsSingleChoice);
            controller.setmIsMultiChoice(mIsMultiChoice);
            controller.setOnSingleChoiceListener(onSingleChoiceListener);
            controller.setOnMultiChoiceListener(onMultiChoiceListener);

//            mController.setOnBtnClickListener(onConfirmListener);
        }
    }
}
