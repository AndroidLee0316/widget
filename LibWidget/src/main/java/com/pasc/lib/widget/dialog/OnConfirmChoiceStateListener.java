package com.pasc.lib.widget.dialog;

import android.support.v4.app.DialogFragment;

import java.io.Serializable;

public abstract class OnConfirmChoiceStateListener<T extends DialogFragment> extends BaseListener {

    public abstract void onConfirm(T dialogFragment, boolean checkState);
}
