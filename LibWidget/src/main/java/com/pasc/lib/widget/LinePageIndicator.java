package com.pasc.lib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by huanglihou519 on 2018/2/12.
 */

public class LinePageIndicator extends LinearLayout {
    private int pageNum = 4;
    private int unSelectedColor;
    private int selectedColor;
    private int current = 0;

    public LinePageIndicator(Context context) {
        super(context);
        init(context);
    }

    public LinePageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        selectedColor = ContextCompat.getColor(context, R.color.line_page_indicator_selected_color);
        unSelectedColor = ContextCompat.getColor(context, R.color.line_page_indicator_unselected_color);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        int viewWidth = DensityUtils.dp2px(8);
        for (int i = 0; i < pageNum; i++) {
            View chunk = new View(this.getContext());
            chunk.setBackgroundColor(unSelectedColor);
            LayoutParams params = new LayoutParams(viewWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(DensityUtils.dp2px(1f), 0, DensityUtils.dp2px(1f), 0);
            chunk.setLayoutParams(params);
            addView(chunk);
        }
        requestLayout();
        select(0);
    }

    public void select(int selectedIndex) {
        setUnSelected(current);
        setSelect(selectedIndex);
        current = selectedIndex;
    }

    private void setUnSelected(int index) {
        View chunk = getChildAt(index);
        chunk.setBackgroundColor(unSelectedColor);
    }

    private void setSelect(int index) {
        View chunk = getChildAt(index);
        chunk.setBackgroundColor(selectedColor);
    }
}
