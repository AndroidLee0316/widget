package com.pasc.lib.widget.button;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.pasc.lib.widget.R;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/9
 * PascToggleButton控件（2.0规范效果）
 */
public class PascToggleButton extends View {
    private Spring spring;
    /** 圆角大小 */
    private float radius;
    /** 开启颜色 */
    private int onColor = Color.parseColor("#27A5F9");
    /** 边框颜色（关闭状态） */
    private int offBorderColor = Color.parseColor("#E2E2E2");
    /** 关闭颜色 */
    private int offColor = offBorderColor;
    /** 手柄颜色 */
    private int spotColor = Color.parseColor("#ffffff");
    /** 边框颜色 */
    private int borderColor = offBorderColor;
    /** 开关状态，默认为关闭状态 */
    private boolean isToggleOn = false;
    /** 边框大小 */
    private int borderWidth = 1;
    /** 是否使用动画，默认为使用 */
    private boolean isAnimate = true;

    /** 画笔 */
    private Paint paint;
    /** 垂直中心 */
    private float centerY;
    /** 按钮的开始和结束位置 */
    private float startX, endX;
    /** 手柄X位置的最小和最大值 */
    private float spotMinX, spotMaxX;
    /** 手柄大小 */
    private int spotSize;
    /** 手柄X位置 */
    private float spotX;
    /** 关闭时内部灰色带高度 */
    private float offLineWidth;

    private RectF rect = new RectF();
    private PascToggleButton.OnToggleChanged listener;

    public PascToggleButton(Context context) {
        this(context, null);
    }

    public PascToggleButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public PascToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    public void setup(AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        SpringSystem springSystem = SpringSystem.create();
        spring = springSystem.createSpring();
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(50, 10));
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                toggleAnimate(isAnimate);
            }
        });
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PascToggleButton);
        offBorderColor = typedArray.getColor(R.styleable.PascToggleButton_tbOffBorderColor, offBorderColor);
        onColor = typedArray.getColor(R.styleable.PascToggleButton_tbOnColor, onColor);
        spotColor = typedArray.getColor(R.styleable.PascToggleButton_tbSpotColor, spotColor);
        offColor = typedArray.getColor(R.styleable.PascToggleButton_tbOffColor, offColor);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.PascToggleButton_tbBorderWidth, borderWidth);
        isAnimate = typedArray.getBoolean(R.styleable.PascToggleButton_tbIsAnimate, isAnimate);
        isToggleOn = typedArray.getBoolean(R.styleable.PascToggleButton_tbIsDefaultOn, isToggleOn);
        typedArray.recycle();
        borderColor = offBorderColor;
        if (isToggleOn) {
            toggleOn();
        }
    }

    private SimpleSpringListener springListener = new SimpleSpringListener() {
        @Override
        public void onSpringUpdate(Spring spring) {
            final double value = spring.getCurrentValue();
            calculateEffect(value);
        }
    };

    /**
     * 设置打开状态的颜色
     */
    public void setOnColor(int onColor) {
        this.onColor = onColor;
        takeEffect(false);
    }

    /**
     * 设置关闭状态的边框颜色
     */
    public void setOffBorderColor(int offBorderColor) {
        this.offBorderColor = offBorderColor;
        takeEffect(false);
    }

    /**
     * 设置关闭状态的颜色
     */
    public void setOffColor(int offColor) {
        this.offColor = offColor;
        takeEffect(false);
    }

    /**
     * 设置打开手柄的颜色
     */
    public void setSpotColor(int spotColor) {
        this.spotColor = spotColor;
        takeEffect(false);
    }

    /**
     * 启用ToggleButton按钮动画
     */
    public void toggleAnimate() {
        toggleAnimate(true);
    }

    /**
     * 设置ToggleButton按钮动画状态
     *
     * @param animate true:开启，false:关闭
     */
    public void toggleAnimate(boolean animate) {
        isToggleOn = !isToggleOn;
        takeEffect(animate);
        if (listener != null) {
            listener.onToggle(isToggleOn);
        }
    }

    /**
     * 将ToggleButton置为开启状态
     */
    public void toggleOn() {
        setToggleOn();
        if (listener != null) {
            listener.onToggle(isToggleOn);
        }
    }

    /**
     * 将ToggleButton置为关闭状态
     */
    public void toggleOff() {
        setToggleOff();
        if (listener != null) {
            listener.onToggle(isToggleOn);
        }
    }

    /**
     * 设置ToggleButton为开启状态，不会触发toggle事件，默认启用动画
     */
    public void setToggleOn() {
        setToggleOn(true);
    }

    /**
     * 设置ToggleButton为开启状态
     *
     * @param animate true:需要动画，false:不需要动画
     */
    public void setToggleOn(boolean animate) {
        isToggleOn = true;
        takeEffect(animate);
    }

    /**
     * 设置ToggleButton为关闭状态，不会触发toggle事件，默认启用动画
     */
    public void setToggleOff() {
        setToggleOff(true);
    }

    /**
     * 设置ToggleButton为关闭状态
     *
     * @param animate true:需要动画，false:不需要动画
     */
    public void setToggleOff(boolean animate) {
        isToggleOn = false;
        takeEffect(animate);
    }

    /**
     * 设置ToggleButton点击状态回调监听
     *
     * @param onToggleChanged 回调
     */
    public void setOnToggleChanged(PascToggleButton.OnToggleChanged onToggleChanged) {
        listener = onToggleChanged;
    }

    /**
     * 是否启用动画
     *
     * @return true:启用，false:关闭
     */
    public boolean isAnimate() {
        return isAnimate;
    }

    /**
     * 设置是否启用动画
     *
     * @param animate true:启用，false:关闭
     */
    public void setAnimate(boolean animate) {
        this.isAnimate = animate;
    }

    /**
     * ToggleButton打开或关闭状态监听
     */
    public interface OnToggleChanged {
        /**
         * 按钮状态回调
         *
         * @param on true:打开，false:关闭
         */
        void onToggle(boolean on);
    }

    private void takeEffect(boolean animate) {
        if (animate) {
            spring.setEndValue(isToggleOn ? 1 : 0);
        } else {
            //这里没有调用spring，所以spring里的当前值没有变更，这里要设置一下，同步两边的当前值
            spring.setCurrentValue(isToggleOn ? 1 : 0);
            calculateEffect(isToggleOn ? 1 : 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.UNSPECIFIED || heightSize == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int width = getWidth();
        final int height = getHeight();
        radius = Math.min(width, height) * 0.5f;
        centerY = radius;
        startX = radius;
        endX = width - radius;
        spotMinX = startX + borderWidth;
        spotMaxX = endX - borderWidth;
        spotSize = height - 4 * borderWidth;
        spotX = isToggleOn ? spotMaxX : spotMinX;
        offLineWidth = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        rect.set(0, 0, getWidth(), getHeight());
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);
        if (offLineWidth > 0) {
            final float cy = offLineWidth * 0.5f;
            rect.set(spotX - cy, centerY - cy, endX + cy, centerY + cy);
            paint.setColor(offColor);
            canvas.drawRoundRect(rect, cy, cy, paint);
        }
        rect.set(spotX - 1 - radius, centerY - radius, spotX + 1.1f + radius, centerY + radius);
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);
        final float spotR = spotSize * 0.5f;
        rect.set(spotX - spotR, centerY - spotR, spotX + spotR, centerY + spotR);
        paint.setColor(spotColor);
        canvas.drawRoundRect(rect, spotR, spotR, paint);
    }

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }

    private void calculateEffect(final double value) {
        spotX = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, spotMinX, spotMaxX);
        offLineWidth = (float) SpringUtil.mapValueFromRangeToRange(1 - value, 0, 1, 10, spotSize);
        final int fb = Color.blue(onColor);
        final int fr = Color.red(onColor);
        final int fg = Color.green(onColor);
        final int tb = Color.blue(offBorderColor);
        final int tr = Color.red(offBorderColor);
        final int tg = Color.green(offBorderColor);
        int sb = (int) SpringUtil.mapValueFromRangeToRange(1 - value, 0, 1, fb, tb);
        int sr = (int) SpringUtil.mapValueFromRangeToRange(1 - value, 0, 1, fr, tr);
        int sg = (int) SpringUtil.mapValueFromRangeToRange(1 - value, 0, 1, fg, tg);
        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);
        borderColor = Color.rgb(sr, sg, sb);
        postInvalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        spring.removeListener(springListener);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        spring.addListener(springListener);
    }
}
