package com.pasc.lib.widget.seriesadapter.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by huanglihou519 on 2018/3/26.
 */

public class NoBugGridLayoutManager extends GridLayoutManager {
    public NoBugGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                  int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NoBugGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public NoBugGridLayoutManager(Context context, int spanCount, int orientation,
                                  boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
