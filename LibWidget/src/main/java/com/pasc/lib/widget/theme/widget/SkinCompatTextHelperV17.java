package com.pasc.lib.widget.theme.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.theme.res.SkinCompatResources;



@RequiresApi(17)
@TargetApi(17)
public class SkinCompatTextHelperV17 extends SkinCompatTextHelper {
    private int mDrawableStartResId = INVALID_ID;
    private int mDrawableEndResId = INVALID_ID;

    public SkinCompatTextHelperV17(TextView view) {
        super(view);
    }

    @Override
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        final Context context = mView.getContext();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SkinCompatTextHelper,
                defStyleAttr, 0);
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableStart)) {
            mDrawableStartResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableStart, INVALID_ID);
            mDrawableStartResId = SkinCompatHelper.checkResourceId(mDrawableStartResId);
        }
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableEnd)) {
            mDrawableEndResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableEnd, INVALID_ID);
            mDrawableEndResId = SkinCompatHelper.checkResourceId(mDrawableEndResId);
        }
        a.recycle();
        super.loadFromAttributes(attrs, defStyleAttr);
    }

    public void onSetCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        mDrawableStartResId = start;
        mDrawableTopResId = top;
        mDrawableEndResId = end;
        mDrawableBottomResId = bottom;
        applyCompoundDrawablesRelativeResource();
    }

    @Override
    protected void applyCompoundDrawablesRelativeResource() {
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null,
                drawableStart = null, drawableEnd = null;
        mDrawableLeftResId = checkResourceId(mDrawableLeftResId);
        if (mDrawableLeftResId != INVALID_ID) {
            drawableLeft = SkinCompatResources.getDrawableCompat(mView.getContext(), mDrawableLeftResId);
        }
        mDrawableTopResId = checkResourceId(mDrawableTopResId);
        if (mDrawableTopResId != INVALID_ID) {
            drawableTop = SkinCompatResources.getDrawableCompat(mView.getContext(), mDrawableTopResId);
        }
        mDrawableRightResId = checkResourceId(mDrawableRightResId);
        if (mDrawableRightResId != INVALID_ID) {
            drawableRight = SkinCompatResources.getDrawableCompat(mView.getContext(), mDrawableRightResId);
        }
        mDrawableBottomResId = checkResourceId(mDrawableBottomResId);
        if (mDrawableBottomResId != INVALID_ID) {
            drawableBottom = SkinCompatResources.getDrawableCompat(mView.getContext(), mDrawableBottomResId);
        }
        if (mDrawableStartResId != INVALID_ID) {
            drawableStart = SkinCompatResources.getDrawableCompat(mView.getContext(), mDrawableStartResId);
        }
        if (drawableStart == null) {
            drawableStart = drawableLeft;
        }
        if (mDrawableEndResId != INVALID_ID) {
            drawableEnd = SkinCompatResources.getDrawableCompat(mView.getContext(), mDrawableEndResId);
        }
        if (drawableEnd == null) {
            drawableEnd = drawableRight;
        }
        if (mDrawableLeftResId != INVALID_ID
                || mDrawableTopResId != INVALID_ID
                || mDrawableRightResId != INVALID_ID
                || mDrawableBottomResId != INVALID_ID
                || mDrawableStartResId != INVALID_ID
                || mDrawableEndResId != INVALID_ID) {
            mView.setCompoundDrawablesWithIntrinsicBounds(drawableStart, drawableTop, drawableEnd, drawableBottom);
        }
    }
}
