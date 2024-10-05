
package com.pasc.lib.widget.tablayout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pasc.lib.widget.util.PascDrawableHelper;


public class PascTabIndicator {

    /**
     * the height of indicator
     */
    private int mIndicatorHeight;
    /**
     * is indicator layout in top of QMUITabSegment?
     */
    private boolean mIndicatorTop = false;
    /**
     * use a drawable to present the indicator
     */
    private @Nullable
    Drawable mIndicatorDrawable;
    /**
     * the width of indicator changed when toggle to different tab
     */
    private boolean mIsIndicatorWidthFollowContent = true;

    /**
     * indicator rect, draw directly
     */
    private Rect mIndicatorRect = null;

    /**
     * indicator paint, draw directly
     */
    private Paint mIndicatorPaint = null;

    public PascTabIndicator(int indicatorHeight, boolean indicatorTop,
                            boolean isIndicatorWidthFollowContent) {
        mIndicatorHeight = indicatorHeight;
        mIndicatorTop = indicatorTop;
        mIsIndicatorWidthFollowContent = isIndicatorWidthFollowContent;
    }

    public PascTabIndicator(@NonNull Drawable drawable, boolean indicatorTop,
                            boolean isIndicatorWidthFollowContent) {
        mIndicatorDrawable = drawable;
        mIndicatorHeight = drawable.getIntrinsicHeight();
        mIndicatorTop = indicatorTop;
        mIsIndicatorWidthFollowContent = isIndicatorWidthFollowContent;
    }

    public boolean isIndicatorWidthFollowContent() {
        return mIsIndicatorWidthFollowContent;
    }

    public boolean isIndicatorTop() {
        return mIndicatorTop;
    }

    protected void updateInfo(int left, int width, int color) {
        if (mIndicatorRect == null) {
            mIndicatorRect = new Rect(left, 0,
                    left + width, 0);
        } else {
            mIndicatorRect.left = left;
            mIndicatorRect.right = left + width;
        }

        if (mIndicatorDrawable != null) {
            PascDrawableHelper.setDrawableTintColor(mIndicatorDrawable, color);
        } else {
            if (mIndicatorPaint == null) {
                mIndicatorPaint = new Paint();
                mIndicatorPaint.setStyle(Paint.Style.FILL);
            }
            mIndicatorPaint.setColor(color);
        }
    }

    protected void draw(Canvas canvas, int viewTop, int viewBottom) {
        if (mIndicatorRect != null) {
            if (mIndicatorTop) {
                mIndicatorRect.top = viewTop;
                mIndicatorRect.bottom = mIndicatorRect.top + mIndicatorHeight;
            } else {
                mIndicatorRect.bottom = viewBottom;
                mIndicatorRect.top = mIndicatorRect.bottom - mIndicatorHeight;
            }
            if (mIndicatorDrawable != null) {
                mIndicatorDrawable.setBounds(mIndicatorRect);
                mIndicatorDrawable.draw(canvas);
            } else {
                canvas.drawRect(mIndicatorRect, mIndicatorPaint);
            }
        }
    }
}