
package com.pasc.lib.widget.tablayout;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pasc.lib.widget.util.PascDrawableHelper;
import com.pasc.lib.widget.util.PascLangHelper;

public class PascTabIcon extends Drawable {

    public static final int TAB_ICON_INTRINSIC = -1;
    private final @NonNull
    Drawable mNormalIconDrawable;
    private final @Nullable
    Drawable mSelectedIconDrawable;

    public PascTabIcon(@NonNull Drawable normalIconDrawable, @Nullable Drawable selectedIconDrawable) {
        mNormalIconDrawable = normalIconDrawable;
        mSelectedIconDrawable = selectedIconDrawable;
        mNormalIconDrawable.setAlpha(255);
        int nw = mNormalIconDrawable.getIntrinsicWidth();
        int nh = mNormalIconDrawable.getIntrinsicHeight();
        mNormalIconDrawable.setBounds(0, 0, nw, nh);
        if (mSelectedIconDrawable != null) {
            mSelectedIconDrawable.setAlpha(0);
            mSelectedIconDrawable.setBounds(0, 0, nw, nh);
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return mNormalIconDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mNormalIconDrawable.getIntrinsicHeight();
    }

    @Override
    public void setAlpha(int alpha) {
        // not used
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // not used
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * set the select faction for PascTabIcon, value must be in [0, 1]
     *
     * @param fraction muse be in [0, 1]
     */
    public void setSelectFraction(float fraction, int color) {

        fraction = PascLangHelper.constrain(fraction, 0f, 1f);
        if (mSelectedIconDrawable == null) {
            PascDrawableHelper.setDrawableTintColor(mNormalIconDrawable, color);
        } else {
            int normalAlpha = (int) (255 * (1 - fraction));
            mNormalIconDrawable.setAlpha(normalAlpha);
            mSelectedIconDrawable.setAlpha(255 - normalAlpha);
        }
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mNormalIconDrawable.draw(canvas);
        if (mSelectedIconDrawable != null) {
            mSelectedIconDrawable.draw(canvas);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mNormalIconDrawable.setBounds(bounds);
        if (mSelectedIconDrawable != null) {
            mSelectedIconDrawable.setBounds(bounds);
        }
    }
}
