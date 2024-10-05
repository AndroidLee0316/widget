package com.pasc.lib.widget.cardheader;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;

public class PaCardHeaderView extends LinearLayout {
  private TextView titleView;
  private TextView subTitleView;
  private ImageView iconView;
  private ImageView moreView;

  private int titleColor;
  private int titleSize;
  private int subTitleColor;
  private int subTitleSize;

  private CharSequence title;
  private CharSequence subTitle;

  public PaCardHeaderView(Context context) {
    this(context, null);
  }

  public PaCardHeaderView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PaCardHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflate(context, R.layout.view_card_header, this);
    titleView = findViewById(R.id.title_view);
    subTitleView = findViewById(R.id.sub_title_view);
    iconView = findViewById(R.id.icon_view);
    moreView = findViewById(R.id.more_view);

    TypedArray array =
            getContext().obtainStyledAttributes(attrs, R.styleable.PaCardHeaderView, defStyleAttr,
                    0);
    CharSequence title = array.getText(R.styleable.PaCardHeaderView_title);
    if (!TextUtils.isEmpty(title)) {
      setTitle(title);
    }
    CharSequence subTitle = array.getText(R.styleable.PaCardHeaderView_sub_title);
    if (!TextUtils.isEmpty(subTitle)) {
      setSubTitle(subTitle);
      subTitleView.setVisibility(VISIBLE);
    } else {
      subTitleView.setVisibility(GONE);
    }
    titleSize = array.getDimensionPixelSize(R.styleable.PaCardHeaderView_title_text_size,
            DensityUtils.dp2px(18));
    titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);

    titleColor = array.getColor(R.styleable.PaCardHeaderView_title_text_color,
            ContextCompat.getColor(context, R.color.pasc_primary_text));
    titleView.setTextColor(titleColor);

    subTitleSize = array.getDimensionPixelSize(R.styleable.PaCardHeaderView_sub_title_text_size,
            DensityUtils.dp2px(13));
    subTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTitleSize);

    subTitleColor = array.getColor(R.styleable.PaCardHeaderView_sub_title_text_color,
            ContextCompat.getColor(context, R.color.pasc_explain_text));
    subTitleView.setTextColor(subTitleColor);

    if (array.hasValue(R.styleable.PaCardHeaderView_card_icon)) {
      iconView.setVisibility(VISIBLE);
      iconView.setImageResource(
              array.getResourceId(R.styleable.PaCardHeaderView_card_icon, R.drawable.bg_default_img));
    } else {
      iconView.setVisibility(GONE);
    }

    final int moreIconRes = array.getResourceId(R.styleable.PaCardHeaderView_more_icon, R.drawable.ic_card_header_more);
    moreView.setImageResource(moreIconRes);
  }

  public void setTitle(CharSequence title) {
    this.title = title;
    titleView.setText(title);
  }

  public void setSubTitle(CharSequence subTitle) {
    this.subTitle = subTitle;
    subTitleView.setText(subTitle);
  }

  public CharSequence getTitle() {
    return title;
  }

  public CharSequence getSubTitle() {
    return subTitle;
  }

  public TextView getTitleView() {
    return titleView;
  }

  public TextView getSubTitleView() {
    return subTitleView;
  }

  public ImageView getIconView() {
    return iconView;
  }

  public ImageView getMoreView() {
    return moreView;
  }
}
