package com.pasc.lib.widget.theme.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;

import static com.pasc.lib.widget.theme.widget.SkinCompatHelper.INVALID_ID;



public class SkinCompatThemeUtils {

    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();

    static final int[] DISABLED_STATE_SET = new int[]{-android.R.attr.state_enabled};
    static final int[] ENABLED_STATE_SET = new int[]{android.R.attr.state_enabled};
    static final int[] WINDOW_FOCUSED_STATE_SET = new int[]{android.R.attr.state_window_focused};
    static final int[] FOCUSED_STATE_SET = new int[]{android.R.attr.state_focused};
    static final int[] ACTIVATED_STATE_SET = new int[]{android.R.attr.state_activated};
    static final int[] ACCELERATED_STATE_SET = new int[]{android.R.attr.state_accelerated};
    static final int[] HOVERED_STATE_SET = new int[]{android.R.attr.state_hovered};
    static final int[] DRAG_CAN_ACCEPT_STATE_SET = new int[]{android.R.attr.state_drag_can_accept};
    static final int[] DRAG_HOVERED_STATE_SET = new int[]{android.R.attr.state_drag_hovered};
    static final int[] PRESSED_STATE_SET = new int[]{android.R.attr.state_pressed};
    static final int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};
    static final int[] SELECTED_STATE_SET = new int[]{android.R.attr.state_selected};
    static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[]{
            -android.R.attr.state_pressed, -android.R.attr.state_focused};
    static final int[] EMPTY_STATE_SET = new int[0];

    private static final int[] TEMP_ARRAY = new int[1];

    private static final int[] APPCOMPAT_COLOR_PRIMARY_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimary
    };
    private static final int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimaryDark
    };
    private static final int[] APPCOMPAT_COLOR_ACCENT_ATTRS = {
            android.support.v7.appcompat.R.attr.colorAccent
    };

    public static int getColorPrimaryResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_PRIMARY_ATTRS);
    }

    public static int getColorPrimaryDarkResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS);
    }

    public static int getColorAccentResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_ACCENT_ATTRS);
    }

    public static int getTextColorPrimaryResId(Context context) {
        return getResId(context, new int[]{android.R.attr.textColorPrimary});
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static int getStatusBarColorResId(Context context) {
        return getResId(context, new int[]{android.R.attr.statusBarColor});
    }

    public static int getWindowBackgroundResId(Context context) {
        return getResId(context, new int[]{android.R.attr.windowBackground});
    }

    private static int getResId(Context context, int[] attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs);
        final int resId = a.getResourceId(0, INVALID_ID);
        a.recycle();
        return resId;
    }

    public static int getThemeAttrColor(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray a = context.obtainStyledAttributes(null, TEMP_ARRAY);
        try {
            int resId = a.getResourceId(0, 0);
            if (resId != 0) {
                return SkinCompatResources.getColor(context, resId);
            }
            return 0;
        } finally {
            a.recycle();
        }
    }

    public static ColorStateList getThemeAttrColorStateList(Context context, int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray a = context.obtainStyledAttributes(null, TEMP_ARRAY);
        try {
            int resId = a.getResourceId(0, 0);
            if (resId != 0) {
                return SkinCompatResources.getColorStateList(context, resId);
            }
            return null;
        } finally {
            a.recycle();
        }
    }

    public static int getDisabledThemeAttrColor(Context context, int attr) {
        final ColorStateList csl = getThemeAttrColorStateList(context, attr);
        if (csl != null && csl.isStateful()) {

            return csl.getColorForState(DISABLED_STATE_SET, csl.getDefaultColor());
        } else {


            final TypedValue tv = getTypedValue();

            context.getTheme().resolveAttribute(android.R.attr.disabledAlpha, tv, true);
            final float disabledAlpha = tv.getFloat();

            return getThemeAttrColor(context, attr, disabledAlpha);
        }
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue = TL_TYPED_VALUE.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            TL_TYPED_VALUE.set(typedValue);
        }
        return typedValue;
    }

    static int getThemeAttrColor(Context context, int attr, float alpha) {
        final int color = getThemeAttrColor(context, attr);
        final int originalAlpha = Color.alpha(color);
        return ColorUtils.setAlphaComponent(color, Math.round(originalAlpha * alpha));
    }
}
