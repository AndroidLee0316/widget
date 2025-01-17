package com.pasc.lib.widget.theme.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.theme.res.SkinCompatResources;



public class SkinCompatTextHelper extends SkinCompatHelper {
    private static final String TAG = SkinCompatTextHelper.class.getSimpleName();

    public static SkinCompatTextHelper create(TextView textView) {
        if (Build.VERSION.SDK_INT >= 17) {
            return new SkinCompatTextHelperV17(textView);
        }
        return new SkinCompatTextHelper(textView);
    }

    final TextView mView;

    private int mTextColorResId = INVALID_ID;
    private int mTextColorHintResId = INVALID_ID;
    protected int mDrawableBottomResId = INVALID_ID;
    protected int mDrawableLeftResId = INVALID_ID;
    protected int mDrawableRightResId = INVALID_ID;
    protected int mDrawableTopResId = INVALID_ID;

    public SkinCompatTextHelper(TextView view) {
        mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        final Context context = mView.getContext();


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SkinCompatTextHelper, defStyleAttr, 0);
        final int ap = a.getResourceId(R.styleable.SkinCompatTextHelper_android_textAppearance, INVALID_ID);

        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableLeft)) {
            mDrawableLeftResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableLeft, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableTop)) {
            mDrawableTopResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableTop, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableRight)) {
            mDrawableRightResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableRight, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableBottom)) {
            mDrawableBottomResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableBottom, INVALID_ID);
        }
        a.recycle();

        if (ap != INVALID_ID) {
            a = context.obtainStyledAttributes(ap, R.styleable.SkinTextAppearance);
            if (a.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
                mTextColorResId = a.getResourceId(R.styleable.SkinTextAppearance_android_textColor, INVALID_ID);
            }
            if (a.hasValue(R.styleable.SkinTextAppearance_android_textColorHint)) {
                mTextColorHintResId = a.getResourceId(
                        R.styleable.SkinTextAppearance_android_textColorHint, INVALID_ID);
            }
            a.recycle();
        }


        a = context.obtainStyledAttributes(attrs, R.styleable.SkinTextAppearance, defStyleAttr, 0);
        if (a.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
            mTextColorResId = a.getResourceId(R.styleable.SkinTextAppearance_android_textColor, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinTextAppearance_android_textColorHint)) {
            mTextColorHintResId = a.getResourceId(
                    R.styleable.SkinTextAppearance_android_textColorHint, INVALID_ID);
        }
        a.recycle();
        applySkin();
    }

    public void onSetTextAppearance(Context context, int resId) {
        final TypedArray a = context.obtainStyledAttributes(resId, R.styleable.SkinTextAppearance);
        if (a.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
            mTextColorResId = a.getResourceId(R.styleable.SkinTextAppearance_android_textColor, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinTextAppearance_android_textColorHint)) {
            mTextColorHintResId = a.getResourceId(R.styleable.SkinTextAppearance_android_textColorHint, INVALID_ID);
        }
        a.recycle();
        applyTextColorResource();
        applyTextColorHintResource();
    }

    private void applyTextColorHintResource() {
        mTextColorHintResId = checkResourceId(mTextColorHintResId);
        if (mTextColorHintResId == R.color.abc_hint_foreground_material_light) {
            return;
        }
        if (mTextColorHintResId != INVALID_ID) {


            try {
                ColorStateList color = SkinCompatResources.getColorStateList(mView.getContext(), mTextColorHintResId);
                mView.setHintTextColor(color);
            } catch (Exception e) {
            }
        }
    }

    private void applyTextColorResource() {
        mTextColorResId = checkResourceId(mTextColorResId);
        if (mTextColorResId == R.color.abc_primary_text_disable_only_material_light
                || mTextColorResId == R.color.abc_secondary_text_material_light) {
            return;
        }
        if (mTextColorResId != INVALID_ID) {


            try {
                ColorStateList color = SkinCompatResources.getColorStateList(mView.getContext(), mTextColorResId);
                mView.setTextColor(color);
            } catch (Exception e) {
            }
        }
    }

    public void onSetCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        mDrawableLeftResId = start;
        mDrawableTopResId = top;
        mDrawableRightResId = end;
        mDrawableBottomResId = bottom;
        applyCompoundDrawablesRelativeResource();
    }

    public void onSetCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        mDrawableLeftResId = left;
        mDrawableTopResId = top;
        mDrawableRightResId = right;
        mDrawableBottomResId = bottom;
        applyCompoundDrawablesResource();
    }

    protected void applyCompoundDrawablesRelativeResource() {
        applyCompoundDrawablesResource();
    }

    protected void applyCompoundDrawablesResource() {
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
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
        if (mDrawableLeftResId != INVALID_ID
                || mDrawableTopResId != INVALID_ID
                || mDrawableRightResId != INVALID_ID
                || mDrawableBottomResId != INVALID_ID) {
            mView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }

    public int getTextColorResId() {
        return mTextColorResId;
    }

    public void applySkin() {
        applyCompoundDrawablesRelativeResource();
        applyTextColorResource();
        applyTextColorHintResource();
    }
}
