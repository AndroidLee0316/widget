package com.pasc.lib.widget.tdialog.listener;

import android.view.View;

import com.pasc.lib.widget.tdialog.TDialog;
import com.pasc.lib.widget.tdialog.base.BindViewHolder;

public interface OnViewClickListener {
    void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog);
}
