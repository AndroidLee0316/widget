package com.pasc.lib.widget.theme.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.theme.res.SkinCompatResources;


public class SkinCompatImageHelper extends SkinCompatHelper {
    private static final String TAG = SkinCompatImageHelper.class.getSimpleName();
    private final ImageView mView;
    private int mSrcResId = INVALID_ID;
    private int mSrcCompatResId = INVALID_ID;

    public SkinCompatImageHelper(ImageView imageView) {
        mView = imageView;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
            a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.SkinCompatImageView, defStyleAttr, 0);
            mSrcResId = a.getResourceId(R.styleable.SkinCompatImageView_android_src, INVALID_ID);
            mSrcCompatResId = a.getResourceId(R.styleable.SkinCompatImageView_srcCompat, INVALID_ID);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        applySkin();
    }

    public void setImageResource(int resId) {
        mSrcResId = resId;
        applySkin();
    }

    public void applySkin() {
        mSrcCompatResId = checkResourceId(mSrcCompatResId);
        if (mSrcCompatResId != INVALID_ID) {
            Drawable drawable = SkinCompatResources.getDrawableCompat(mView.getContext(), mSrcCompatResId);
            if (drawable != null) {
                mView.setImageDrawable(drawable);
            }
        } else {
            mSrcResId = checkResourceId(mSrcResId);
            if (mSrcResId == INVALID_ID) {
                return;
            }
            Drawable drawable = SkinCompatResources.getDrawableCompat(mView.getContext(), mSrcResId);
            if (drawable != null) {
                mView.setImageDrawable(drawable);
            }
        }
    }
}
