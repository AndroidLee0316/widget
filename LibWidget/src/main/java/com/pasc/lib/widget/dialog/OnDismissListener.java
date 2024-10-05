package com.pasc.lib.widget.dialog;

import android.support.v4.app.DialogFragment;

import java.io.Serializable;

public abstract class OnDismissListener<T extends DialogFragment> extends BaseListener {

    public abstract void onDismiss(T dialogFragment);
}
