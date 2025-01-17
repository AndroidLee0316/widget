package com.pasc.lib.widget.theme.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.theme.res.SkinCompatResources;



public class SkinCompatBackgroundHelper extends SkinCompatHelper {
    private final View mView;

    private int mBackgroundResId = INVALID_ID;

    public SkinCompatBackgroundHelper(View view) {
        mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.SkinBackgroundHelper, defStyleAttr, 0);
        try {
            if (a.hasValue(R.styleable.SkinBackgroundHelper_android_background)) {
                mBackgroundResId = a.getResourceId(
                        R.styleable.SkinBackgroundHelper_android_background, INVALID_ID);
            }
        } finally {
            a.recycle();
        }
        applySkin();
    }

    public void onSetBackgroundResource(int resId) {
        mBackgroundResId = resId;

        applySkin();
    }

    public void applySkin() {
        mBackgroundResId = checkResourceId(mBackgroundResId);
        if (mBackgroundResId == INVALID_ID) {
            return;
        }
        Drawable drawable = SkinCompatResources.getDrawableCompat(mView.getContext(), mBackgroundResId);
        if (drawable != null) {
            int paddingLeft = mView.getPaddingLeft();
            int paddingTop = mView.getPaddingTop();
            int paddingRight = mView.getPaddingRight();
            int paddingBottom = mView.getPaddingBottom();
            ViewCompat.setBackground(mView, drawable);
            mView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
    }
}
