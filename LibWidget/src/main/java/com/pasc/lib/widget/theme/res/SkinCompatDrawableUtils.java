package com.pasc.lib.widget.theme.res;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.graphics.drawable.DrawableWrapper;

import com.pasc.lib.widget.theme.utils.SkinCompatVersionUtils;

class SkinCompatDrawableUtils {

    private static final String VECTOR_DRAWABLE_CLAZZ_NAME
            = "android.graphics.drawable.VectorDrawable";


    static void fixDrawable(@NonNull final Drawable drawable) {
        if (Build.VERSION.SDK_INT == 21
                && VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable.getClass().getName())) {
            fixVectorDrawableTinting(drawable);
        }
    }


    public static boolean canSafelyMutateDrawable(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT < 15 && drawable instanceof InsetDrawable) {
            return false;
        } else if (Build.VERSION.SDK_INT < 15 && drawable instanceof GradientDrawable) {


            return false;
        } else if (Build.VERSION.SDK_INT < 17 && drawable instanceof LayerDrawable) {
            return false;
        }

        if (drawable instanceof DrawableContainer) {

            final Drawable.ConstantState state = drawable.getConstantState();
            if (state instanceof DrawableContainer.DrawableContainerState) {
                final DrawableContainer.DrawableContainerState containerState =
                        (DrawableContainer.DrawableContainerState) state;
                for (final Drawable child : containerState.getChildren()) {
                    if (!canSafelyMutateDrawable(child)) {
                        return false;
                    }
                }
            }
        } else if (SkinCompatVersionUtils.isV4DrawableWrapper(drawable)) {
            return canSafelyMutateDrawable(SkinCompatVersionUtils.getV4DrawableWrapperWrappedDrawable(drawable));
        } else if (SkinCompatVersionUtils.isV4WrappedDrawable(drawable)) {
            return canSafelyMutateDrawable(SkinCompatVersionUtils.getV4WrappedDrawableWrappedDrawable(drawable));
        } else if (drawable instanceof DrawableWrapper) {
            return canSafelyMutateDrawable(((DrawableWrapper) drawable).getWrappedDrawable());
        } else if (drawable instanceof ScaleDrawable) {
            return canSafelyMutateDrawable(((ScaleDrawable) drawable).getDrawable());
        }

        return true;
    }


    private static void fixVectorDrawableTinting(final Drawable drawable) {
        final int[] originalState = drawable.getState();
        if (originalState == null || originalState.length == 0) {

            drawable.setState(SkinCompatThemeUtils.CHECKED_STATE_SET);
        } else {

            drawable.setState(SkinCompatThemeUtils.EMPTY_STATE_SET);
        }

        drawable.setState(originalState);
    }
}
