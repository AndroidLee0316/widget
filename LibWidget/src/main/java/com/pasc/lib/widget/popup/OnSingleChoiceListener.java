package com.pasc.lib.widget.popup;

import android.widget.PopupWindow;

/**
 * 单选监听.
 */
public interface OnSingleChoiceListener<T extends PopupWindow> {

    /**
     * 选中了某一项.
     *
     * @param popupWindow 弹出框.
     * @param position    选中项的位置.
     */
    void onSingleChoice(T popupWindow, int position);
}
