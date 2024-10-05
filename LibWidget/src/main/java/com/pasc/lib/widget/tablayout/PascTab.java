
package com.pasc.lib.widget.tablayout;

import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.view.Gravity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PascTab {
    public static final int ICON_POSITION_LEFT = 0;
    public static final int ICON_POSITION_TOP = 1;
    public static final int ICON_POSITION_RIGHT = 2;
    public static final int ICON_POSITION_BOTTOM = 3;

    static final int NO_SIGN_COUNT_AND_RED_POINT = -1;
    static final int RED_POINT_SIGN_COUNT = 0;

    @IntDef(value = {
            ICON_POSITION_LEFT,
            ICON_POSITION_TOP,
            ICON_POSITION_RIGHT,
            ICON_POSITION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconPosition {
    }

    boolean allowIconDrawOutside;
    int iconTextGap;
    int normalTextSize;
    int selectedTextSize;
    Typeface normalTypeface;
    Typeface selectedTypeface;
    int normalColor;
    int selectedColor;
    int normalTabIconWidth = PascTabIcon.TAB_ICON_INTRINSIC;
    int normalTabIconHeight = PascTabIcon.TAB_ICON_INTRINSIC;
    float selectedTabIconScale = 1f;
    PascTabIcon tabIcon = null;
    int contentWidth = 0;
    int contentLeft = 0;
    @IconPosition
    int iconPosition = ICON_POSITION_TOP;
    int gravity = Gravity.CENTER;
    private CharSequence text;
    int signCountDigits = 2;
    int signCountLeftMarginWithIconOrText = 0;
    int signCountBottomMarginWithIconOrText = 0;
    int signCount = NO_SIGN_COUNT_AND_RED_POINT;

    float rightSpaceWeight = 0f;
    float leftSpaceWeight = 0f;
    int leftAddonMargin = 0;
    int rightAddonMargin = 0;


    PascTab(CharSequence text) {
        this.text = text;
    }


    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    public int getIconPosition() {
        return iconPosition;
    }

    public void setIconPosition(@IconPosition int iconPosition) {
        this.iconPosition = iconPosition;
    }

    public void setSpaceWeight(float leftWeight, float rightWeight) {
        leftSpaceWeight = leftWeight;
        rightSpaceWeight = rightWeight;
    }


    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public void setRedPoint() {
        this.signCount = RED_POINT_SIGN_COUNT;
    }

    public int getSignCount() {
        return this.signCount;
    }

    public boolean isRedPointShowing() {
        return this.signCount == RED_POINT_SIGN_COUNT;
    }

    public void clearSignCountOrRedPoint() {
        this.signCount = NO_SIGN_COUNT_AND_RED_POINT;
    }


    public int getNormalColor() {
        return normalColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public int getNormalTextSize() {
        return normalTextSize;
    }

    public int getSelectedTextSize() {
        return selectedTextSize;
    }

    public PascTabIcon getTabIcon() {
        return tabIcon;
    }

    public Typeface getNormalTypeface() {
        return normalTypeface;
    }

    public Typeface getSelectedTypeface() {
        return selectedTypeface;
    }

    public int getNormalTabIconWidth() {
        if (normalTabIconWidth == PascTabIcon.TAB_ICON_INTRINSIC && tabIcon != null) {
            return tabIcon.getIntrinsicWidth();
        }
        return normalTabIconWidth;
    }

    public int getNormalTabIconHeight() {
        if (normalTabIconHeight == PascTabIcon.TAB_ICON_INTRINSIC && tabIcon != null) {
            return tabIcon.getIntrinsicWidth();
        }
        return normalTabIconHeight;
    }

    public float getSelectedTabIconScale() {
        return selectedTabIconScale;
    }

    public int getIconTextGap() {
        return iconTextGap;
    }

    public boolean isAllowIconDrawOutside() {
        return allowIconDrawOutside;
    }
}
