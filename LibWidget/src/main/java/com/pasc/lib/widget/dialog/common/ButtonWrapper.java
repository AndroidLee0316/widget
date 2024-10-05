package com.pasc.lib.widget.dialog.common;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/2
 */
public class ButtonWrapper {
    private static final int MAX_BUTTON_COUNTS = 3;
    private List<CharSequence> items;
    private List<Integer> textColors;
    private List<Integer> buttonBgs;

    public List<CharSequence> getItems() {
        return items;
    }

    public List<Integer> getTextColors() {
        return textColors;
    }

    public List<Integer> getButtonBgs() {
        return buttonBgs;
    }

    private ButtonWrapper(List<CharSequence> items, List<Integer> textColors, List<Integer> buttonBgs) {
        this.textColors = textColors;
        this.buttonBgs = buttonBgs;
        this.items = items;
    }

    public static ButtonWrapper wapButton(CharSequence text, @ColorRes int textColor, @DrawableRes int buttonBgResId) {
        if (text == null) {
            throw new IllegalArgumentException("传入的参数不合法！");
        }
        ArrayList<CharSequence> texts = new ArrayList<>();
        ArrayList<Integer> textColors = new ArrayList<>();
        ArrayList<Integer> buttonBgs = new ArrayList<>();
        texts.add(text);
        textColors.add(textColor);
        buttonBgs.add(buttonBgResId);
        return new ButtonWrapper(texts, textColors, buttonBgs);
    }

    public static ButtonWrapper wapButtons(List<CharSequence> items, List<Integer> textColors, List<Integer> buttonBgResIds) {
        if (items == null || items.size() > MAX_BUTTON_COUNTS) {
            throw new IllegalArgumentException("传入的items为null或items的size大于最大数量3！！！");
        }

        if ((items.size() != textColors.size()) || (items.size() != buttonBgResIds.size())) {
            throw new IllegalArgumentException("传入的参数不合法！该处需要items.size() == textColors.size() == buttonBgResIds.size()");
        }

        return new ButtonWrapper(new ArrayList<>(items), new ArrayList<>(textColors), new ArrayList<>(buttonBgResIds));
    }
}
