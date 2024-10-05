package com.pasc.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by chenruihan410.
 */
public class PascRatioImageView extends AppCompatImageView {

  private static final float DEFAULT_ASPECT_RATIO = 1;
  public static final int REFERENCE_NONE = 0;
  public static final int REFERENCE_WIDTH = 1;
  public static final int REFERENCE_HEIGHT = 2;
  private float widthRatio;
  private float heightRatio;
  private int ratioRef;

  public PascRatioImageView(final Context context) {
    super(context);
    init(context, null, 0, 0);
  }

  public PascRatioImageView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0, 0);
  }

  public PascRatioImageView(final Context context, final AttributeSet attrs,
      final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr, 0);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    if (attrs == null) {
      return;
    }
    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.PascRatioImageView, defStyleAttr,
            defStyleRes);

    widthRatio =
        typedArray.getFloat(R.styleable.PascRatioImageView_priv_widthRatio, DEFAULT_ASPECT_RATIO);
    heightRatio =
        typedArray.getFloat(R.styleable.PascRatioImageView_priv_heightRatio, DEFAULT_ASPECT_RATIO);
    ratioRef = typedArray.getInt(R.styleable.PascRatioImageView_priv_ratioRef, REFERENCE_NONE);

    typedArray.recycle();

    validateRatio(widthRatio);
    validateRatio(heightRatio);
  }

  @Override
  protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (ratioRef == REFERENCE_WIDTH) {
      int measuredWidth = getMeasuredWidth();
      if (measuredWidth > 0 && widthRatio > 0) {
        float sizePerRatio = (float) measuredWidth / widthRatio;
        int height = Math.round(sizePerRatio * heightRatio);
        setMeasuredDimension(measuredWidth, height);
      }
    } else if (ratioRef == REFERENCE_HEIGHT) {
      int measuredHeight = getMeasuredHeight();
      if (measuredHeight > 0 && heightRatio > 0) {
        float sizePerRatio = (float) measuredHeight / heightRatio;
        int width = Math.round(sizePerRatio * widthRatio);
        setMeasuredDimension(width, measuredHeight);
      }
    }
  }

  public float getWidthRatio() {
    return widthRatio;
  }

  public void setWidthRatio(float widthRatio) {
    validateRatio(widthRatio);
    this.widthRatio = widthRatio;
  }

  public float getHeightRatio() {
    return heightRatio;
  }

  public void setHeightRatio(float heightRatio) {
    validateRatio(heightRatio);
    this.heightRatio = heightRatio;
  }

  private void validateRatio(float ratio) {
    if (ratio <= 0f) {
      throw new IllegalArgumentException("ratio > 0");
    }
  }

  public int getRatioRef() {
    return ratioRef;
  }

  public void setRatioRef(int ratioRef) {
    this.ratioRef = ratioRef;
  }
}