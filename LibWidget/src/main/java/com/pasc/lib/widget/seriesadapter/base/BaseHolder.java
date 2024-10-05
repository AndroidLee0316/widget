package com.pasc.lib.widget.seriesadapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by huanglihou519.
 */

public abstract class BaseHolder extends RecyclerView.ViewHolder {
    public ItemModel model;


    public BaseHolder(View itemView) {
        super(itemView);
    }


    public void unBind() {
    }
}
