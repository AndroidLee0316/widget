package com.pasc.lib.widget.theme.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;



public class SkinCompatImageView extends AppCompatImageView implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private SkinCompatImageHelper mImageHelper;

    public SkinCompatImageView(Context context) {
        this(context, null);
    }

    public SkinCompatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);

        mImageHelper = new SkinCompatImageHelper(this);
        mImageHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {

        if (mImageHelper != null) {
            mImageHelper.setImageResource(resId);
        }
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
        if (mImageHelper != null) {
            mImageHelper.applySkin();
        }
    }

}
