package com.pasc.lib.widget.dialog;

import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import java.io.Serializable;

public abstract class OnConfirmListener<T extends DialogFragment> extends BaseListener{

    public abstract void onConfirm(T dialogFragment);
}
