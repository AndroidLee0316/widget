package com.pasc.lib.widget.seriesadapter.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by huanglihou519.
 */

public abstract class ItemModel {
    private static final int DEFAULT_SPAN_COUNT = 1;

    public abstract int layoutId();

    public long id() {
        return RecyclerView.NO_ID;
    }

    public int spanCount() {
        return DEFAULT_SPAN_COUNT;
    }
}
