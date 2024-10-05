
package com.pasc.lib.widget.tablayout;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.pasc.lib.widget.DensityUtils;


/**
 * use {@link PascTabLayout#tabBuilder()} to get a instance
 */
public class PascTabBuilder {
    /**
     * icon in normal state
     */
    private @Nullable
    Drawable normalDrawable;
    /**
     * icon in selected state
     */
    private @Nullable
    Drawable selectedDrawable;
    /**
     * change icon by tint color, if true, selectedDrawable will not work
     */
    private boolean dynamicChangeIconColor = false;
    /**
     * text size in normal state
     */
    private int normalTextSize;
    /**
     * text size in selected state
     */
    private int selectTextSize;

    /**
     * text color(icon color in if dynamicChangeIconColor == true) in  normal state
     */
    private int normalColor;
    /**
     * text color(icon color in if dynamicChangeIconColor == true) in  selected state
     */
    private int selectedColor;
    /**
     * icon position(left/top/right/bottom)
     */
    private @PascTab.IconPosition
    int iconPosition = PascTab.ICON_POSITION_TOP;
    /**
     * gravity of text
     */
    private int gravity = Gravity.CENTER;
    /**
     * text
     */
    private CharSequence text;

    /**
     * text typeface in normal state
     */
    private Typeface normalTypeface;

    /**
     * text typeface in selected state
     */
    private Typeface selectedTypeface;

    /**
     * width of tab icon in normal state
     */
    private int normalTabIconWidth = PascTabIcon.TAB_ICON_INTRINSIC;
    /**
     * height of tab icon in normal state
     */
    int normalTabIconHeight = PascTabIcon.TAB_ICON_INTRINSIC;
    /**
     * scale of tab icon in selected state
     */
    float selectedTabIconScale = 1f;

    /**
     * signCount or redPoint
     */
    private int signCount = PascTab.NO_SIGN_COUNT_AND_RED_POINT;

    /**
     * max signCount digits, if the number is over the digits, use 'xx+' to present
     * if signCountDigits == 2 and number is 110, then component will show '99+'
     */
    private int signCountDigits = 2;
    /**
     * the margin left of signCount(redPoint) view
     */
    private int signCountLeftMarginWithIconOrText;
    /**
     * the margin top of signCount(redPoint) view
     */
    private int signCountBottomMarginWithIconOrText;

    /**
     * the gap between icon and text
     */
    private int iconTextGap;

    /**
     * allow icon draw outside of tab view
     */
    private boolean allowIconDrawOutside = true;


    PascTabBuilder(Context context) {
        iconTextGap = DensityUtils.dip2px(context, 2);
        normalTextSize = selectTextSize = DensityUtils.dip2px(context, 12);
        signCountLeftMarginWithIconOrText = DensityUtils.dip2px(context, 3);
        signCountBottomMarginWithIconOrText = signCountLeftMarginWithIconOrText;
    }

    PascTabBuilder(PascTabBuilder other) {
        this.normalDrawable = other.normalDrawable;
        this.selectedDrawable = other.selectedDrawable;
        this.dynamicChangeIconColor = other.dynamicChangeIconColor;
        this.normalTextSize = other.normalTextSize;
        this.selectTextSize = other.selectTextSize;
        this.normalColor = other.normalColor;
        this.selectedColor = other.selectedColor;
        this.iconPosition = other.iconPosition;
        this.gravity = other.gravity;
        this.text = other.text;
        this.signCount = other.signCount;
        this.signCountDigits = other.signCountDigits;
        this.signCountLeftMarginWithIconOrText = other.signCountLeftMarginWithIconOrText;
        this.signCountBottomMarginWithIconOrText = other.signCountBottomMarginWithIconOrText;
        this.normalTypeface = other.normalTypeface;
        this.selectedTypeface = other.selectedTypeface;
        this.normalTabIconWidth = other.normalTabIconWidth;
        this.normalTabIconHeight = other.normalTabIconHeight;
        this.selectedTabIconScale = other.selectedTabIconScale;
        this.iconTextGap = other.iconTextGap;
        this.allowIconDrawOutside = other.allowIconDrawOutside;
    }

    public PascTabBuilder setAllowIconDrawOutside(boolean allowIconDrawOutside) {
        this.allowIconDrawOutside = allowIconDrawOutside;
        return this;
    }

    public PascTabBuilder setNormalDrawable(Drawable normalDrawable) {
        this.normalDrawable = normalDrawable;
        return this;
    }

    public PascTabBuilder setSelectedDrawable(Drawable selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
        return this;
    }

    public PascTabBuilder setTextSize(int normalTextSize, int selectedTextSize) {
        this.normalTextSize = normalTextSize;
        this.selectTextSize = selectedTextSize;
        return this;
    }

    public PascTabBuilder setTypeface(Typeface normalTypeface, Typeface selectedTypeface) {
        this.normalTypeface = normalTypeface;
        this.selectedTypeface = selectedTypeface;
        return this;
    }

    public PascTabBuilder setNormalIconSizeInfo(int normalWidth, int normalHeight) {
        this.normalTabIconWidth = normalWidth;
        this.normalTabIconHeight = normalHeight;
        return this;
    }

    public PascTabBuilder setSelectedIconScale(float selectedScale) {
        this.selectedTabIconScale = selectedScale;
        return this;
    }

    public PascTabBuilder setIconTextGap(int iconTextGap) {
        this.iconTextGap = iconTextGap;
        return this;
    }

    public PascTabBuilder setSignCount(int signCount) {
        this.signCount = signCount;
        return this;
    }

    public PascTabBuilder setSignCountMarginInfo(int digit,
                                                 int leftMarginWithIconOrText, int bottomMarginWithIconOrText) {
        this.signCountDigits = digit;
        this.signCountLeftMarginWithIconOrText = leftMarginWithIconOrText;
        this.signCountBottomMarginWithIconOrText = bottomMarginWithIconOrText;
        return this;
    }

    public PascTabBuilder setColor(int normalColor, int selectedColor) {
        this.normalColor = normalColor;
        this.selectedColor = selectedColor;
        return this;
    }

    public PascTabBuilder setDynamicChangeIconColor(boolean dynamicChangeIconColor) {
        this.dynamicChangeIconColor = dynamicChangeIconColor;
        return this;
    }

    public PascTabBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public PascTabBuilder setIconPosition(@PascTab.IconPosition int iconPosition) {
        this.iconPosition = iconPosition;
        return this;
    }

    public PascTabBuilder setText(CharSequence text) {
        this.text = text;
        return this;
    }

    public PascTab build() {
        PascTab tab = new PascTab(this.text);
        if (normalDrawable != null) {
            if (dynamicChangeIconColor || selectedDrawable == null) {
                tab.tabIcon = new PascTabIcon(normalDrawable, null);
            } else {
                tab.tabIcon = new PascTabIcon(normalDrawable, selectedDrawable);
            }
            tab.tabIcon.setBounds(0, 0, normalTabIconWidth, normalTabIconHeight);
        }
        tab.normalTabIconWidth = this.normalTabIconWidth;
        tab.normalTabIconHeight = this.normalTabIconHeight;
        tab.selectedTabIconScale = this.selectedTabIconScale;
        tab.gravity = this.gravity;
        tab.iconPosition = this.iconPosition;
        tab.normalTextSize = this.normalTextSize;
        tab.selectedTextSize = this.selectTextSize;
        tab.normalTypeface = this.normalTypeface;
        tab.selectedTypeface = this.selectedTypeface;
        tab.normalColor = this.normalColor;
        tab.selectedColor = this.selectedColor;
        tab.signCount = this.signCount;
        tab.signCountDigits = this.signCountDigits;
        tab.signCountLeftMarginWithIconOrText = this.signCountLeftMarginWithIconOrText;
        tab.signCountBottomMarginWithIconOrText = this.signCountBottomMarginWithIconOrText;
        tab.iconTextGap = this.iconTextGap;
        return tab;
    }
}
