package com.pasc.lib.widget.catalog;

/**
 * Created by Coding小僧 on 2019/4/24
 *
 * @since 1.0
 */
public class Catalog {
    private int normalTextColor;
    private int selectedTextColor;
    private int normalBgColor;
    private int selectedBgColor;
    private int itemHeight;
    private int textSize;

    public Catalog(int normalTextColor, int selectedTextColor, int normalBgColor, int selectedBgColor, int itemHeight, int textSize) {
        this.normalTextColor = normalTextColor;
        this.selectedTextColor = selectedTextColor;
        this.normalBgColor = normalBgColor;
        this.selectedBgColor = selectedBgColor;
        this.itemHeight = itemHeight;
        this.textSize = textSize;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public int getNormalBgColor() {
        return normalBgColor;
    }

    public void setNormalBgColor(int normalBgColor) {
        this.normalBgColor = normalBgColor;
    }

    public int getSelectedBgColor() {
        return selectedBgColor;
    }

    public void setSelectedBgColor(int selectedBgColor) {
        this.selectedBgColor = selectedBgColor;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
