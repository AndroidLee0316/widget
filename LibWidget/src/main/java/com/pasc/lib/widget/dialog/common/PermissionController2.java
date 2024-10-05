package com.pasc.lib.widget.dialog.common;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.DialogFragmentInterface;

import java.io.Serializable;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/3/29
 * 辅助类{@link PermissionController2}
 */
public class PermissionController2 implements Serializable{
    //是否有取消按钮。 true:有，false:无
    private boolean isCloseImgVisible;
    //icon图标资源id
    private int iconResId;
    //关闭按钮图标资源id
    private int closeResId;
    //标题
    private CharSequence title;
    //描述
    private CharSequence desc;
    //button内容包装类
    private ButtonWrapper buttonWrapper;
    //弹窗是否可点击蒙层或者手机虚拟返回按钮关闭，
    private boolean isClosable;

    public DialogFragmentInterface.OnClickListener<PermissionDialogFragment2> onClickListener;
    public DialogFragmentInterface.OnCancelListener<PermissionDialogFragment2> onCancelListener;
    public DialogFragmentInterface.OnDismissListener<PermissionDialogFragment2> onDismissListener;


    public boolean isCloseImgVisible() {
        return isCloseImgVisible;
    }

    public void setCloseImgVisible(boolean closeImgVisible) {
        this.isCloseImgVisible = closeImgVisible;
    }

    public boolean isClosable() {
        return isClosable;
    }

    public void setClosable(boolean closable) {
        isClosable = closable;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getCloseResId() {
        return closeResId;
    }

    public void setCloseResId(int closeResId) {
        this.closeResId = closeResId;
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

    public ButtonWrapper getButtonWrapper() {
        return buttonWrapper;
    }

    public void setButtonWrapper(ButtonWrapper buttonWrapper) {
        this.buttonWrapper = buttonWrapper;
    }

    public static class ControllerParams {
        //是否有取消按钮。 true:有，false:无
        public boolean isCloseImgVisible = true;
        //icon图标资源id
        public int iconResId;
        //关闭按钮图标资源id
        public int closeResId = R.drawable.ic_close;
        //标题
        public CharSequence title;
        //描述
        public CharSequence desc;
        //button内容包装类
        public ButtonWrapper buttonWrapper;
        //弹窗是否可点击蒙层或者手机虚拟返回按钮关闭，
        public boolean isClosable = true;

        public DialogFragmentInterface.OnClickListener<PermissionDialogFragment2> onClickListener;
        public DialogFragmentInterface.OnCancelListener<PermissionDialogFragment2> onCancelListener;
        public DialogFragmentInterface.OnDismissListener<PermissionDialogFragment2> onDismissListener;

        public void apply(PermissionController2 controller) {
            controller.setCloseImgVisible(isCloseImgVisible);
            controller.setIconResId(iconResId);
            controller.setCloseResId(closeResId);
            controller.setTitle(title);
            controller.setDesc(desc);
            controller.setClosable(isClosable);
            controller.setButtonWrapper(buttonWrapper);
            controller.onCancelListener = onCancelListener;
            controller.onDismissListener = onDismissListener;
            controller.onClickListener = onClickListener;
        }
    }
}
