package com.pasc.lib.widget.dialog;

import android.support.v4.app.DialogFragment;

/**
 * 已过时，新的控件botton事件监听请使用{@link com.pasc.lib.widget.dialog.DialogFragmentInterface.OnClickListener}
 */
@Deprecated
public abstract class OnButtonClickListener<T extends DialogFragment> extends BaseListener{

    public abstract void onButtonClick(T dialogFragment);
}
