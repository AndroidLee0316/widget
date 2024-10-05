package com.pasc.lib.widget.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;

public class PascSearchBar extends LinearLayout {
  /** 模式。MODE_EDIT：可编辑，MODE_CLICKABLE：不可编辑 */
  private static final int MODE_EDIT = 0;
  private static final int MODE_CLICKABLE = 1;

  /** 是否有下划线 */
  private boolean mEnableUnderDivider;
  /** 下划线的高度 */
  private int mUnderDividerHeight;
  /** 是否支持沉浸式 */
  private boolean mSupportTranslucentStatusBar;
  /** 搜索栏的高度 */
  private int mSearchBarHeight;
  /** 下划线Paint */
  private Paint mDividerPaint;
  /** 搜索内容 */
  private String mSearchContent;
  /** 模式 */
  @Mode
  private int mMode;

  /** 关闭按钮（左上角） */
  private ImageButton mCloseButton;
  /** 文字输入框 */
  private ClearEditText mSearchEditText;
  /** 搜索按钮（右上角） */
  private TextView mSearchButton;
  /** 搜索内容container（不可编辑模式） */
  private View mCenterSearchContainer;
  /** 搜素内容textView（不可编辑模式） */
  private TextView mCenterSearchView;

  /** 关闭点击listener */
  private OnCloseListener mCloseListener;
  /** 文字输入变化listener */
  private OnQueryTextListener mQueryTextListener;
  /** 搜索按钮点击listener */
  private OnClickListener mSearchClickListener;
  /** 搜索搜索区域（不可编辑模式）listener */
  private OnClickListener mCenterSearchClickListener;

  /**
   * 设置搜索内容
   *
   * @param searchContent 搜索内容
   */
  public void setSearchContent(String searchContent) {
    this.mSearchContent = searchContent;
    mSearchEditText.setText(searchContent);
  }

  /**
   * 获取搜索内容
   *
   * @return 搜索内容
   */
  public String getSearchContent() {
    return mSearchContent;
  }

  /**
   * 设置搜索模式
   *
   * @param mode {@link Mode#MODE_EDIT}:可编辑，{@link Mode#MODE_CLICKABLE}:不可编辑
   */
  public void setMode(@Mode int mode) {
    this.mMode = mode;
    if (mMode == MODE_EDIT) {
      mCenterSearchContainer.setVisibility(GONE);
      mCloseButton.setVisibility(VISIBLE);
      mSearchEditText.setVisibility(VISIBLE);
      mSearchButton.setVisibility(VISIBLE);
    } else {
      mCloseButton.setVisibility(GONE);
      mSearchEditText.setVisibility(GONE);
      mSearchButton.setVisibility(GONE);
      mCenterSearchContainer.setVisibility(VISIBLE);
    }
  }

  /**
   * 设置搜索按钮点击事件监听
   *
   * @param searchClickListener 回调
   */
  public void setSearchClickListener(OnClickListener searchClickListener) {
    this.mSearchClickListener = searchClickListener;
  }

  /**
   * 设置关闭按钮点击事件监听
   *
   * @param closeListener 监听
   */
  public void setCloseListener(OnCloseListener closeListener) {
    this.mCloseListener = closeListener;
  }

  /**
   * 设置搜索内容变化监听，
   *
   * @param queryTextListener 监听
   */
  public void setQueryTextListener(OnQueryTextListener queryTextListener) {
    this.mQueryTextListener = queryTextListener;
  }

  /**
   * 设置搜索区域点击事件监听(不可编辑模式下)
   *
   * @param centerSearchClickListener 监听
   */
  public void setCenterSearchClickListener(OnClickListener centerSearchClickListener) {
    this.mCenterSearchClickListener = centerSearchClickListener;
  }

  /**
   * 设置是否有关闭按钮
   *
   * @param enable true：有搜索按钮，false:无搜索按钮
   */
  public void enableCloseButton(boolean enable) {
    if (mMode == MODE_CLICKABLE) throw new IllegalStateException("在clickable mode下不能调用该方法");

    mCloseButton.setVisibility(enable ? VISIBLE : GONE);
    LinearLayout.LayoutParams lp = (LayoutParams) mSearchEditText.getLayoutParams();
    lp.leftMargin = DensityUtils.dp2px(enable ? 0 : 15);
  }


  private final OnClickListener onClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      if (v == mCloseButton) {
        onCloseClicked();
      } else if (v == mSearchButton) {
        onSearchClicked();
      } else if (v == mCenterSearchContainer) {
        onCenterSearchClicked();
      }
    }
  };

  private void onCenterSearchClicked() {
    if (mCenterSearchClickListener != null) {
      mCenterSearchClickListener.onClick(mCenterSearchView);
    }
  }

  public PascSearchBar(Context context) {
    this(context, null);
  }

  public PascSearchBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PascSearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater inflater = LayoutInflater.from(getContext());
    inflater.inflate(R.layout.search_bar, this, true);
    mCloseButton = findViewById(R.id.close_view);
    mSearchEditText = findViewById(R.id.search_edit_view);
    mSearchButton = findViewById(R.id.search_button);
    mCenterSearchContainer = findViewById(R.id.center_search_container);
    mCenterSearchView = findViewById(R.id.center_search_view);
    init(attrs, defStyleAttr);
  }

  private void init(@Nullable AttributeSet attrs, int defStyleAttr) {
    TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PascSearchBar, defStyleAttr, 0);
    //设置搜索模式
    setMode(array.getInt(R.styleable.PascSearchBar_mode, MODE_EDIT));
    //搜索按钮文字大小
    int menuTextSize =
            array.getDimensionPixelSize(R.styleable.PascSearchBar_menu_text_size, DensityUtils.dp2px(15));
    //搜索按钮文字颜色
    int menuTextColor =
            array.getColor(R.styleable.PascSearchBar_menu_text_color, Color.parseColor("#27A5F9"));
    //搜索栏的高度
    mSearchBarHeight =
            array.getDimensionPixelSize(R.styleable.PascSearchBar_android_height, DensityUtils.dp2px(44));
    if (mSearchBarHeight == ViewGroup.LayoutParams.MATCH_PARENT
            || mSearchBarHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
      throw new IllegalArgumentException("高度必须为一个指定的值，不能为 MATCH_PARENT 或者 WRAP_CONTENT ");
    }

    //hint字符串
    if (array.hasValue(R.styleable.PascSearchBar_android_hint)) {
      String searchHint = array.getString(R.styleable.PascSearchBar_android_hint);
      mSearchEditText.setHint(searchHint);
      mCenterSearchView.setHint(searchHint);
    }

    //hint文字颜色
    if (array.hasValue(R.styleable.PascSearchBar_android_textColorHint)) {
      int textColorHint = array.getColor(R.styleable.PascSearchBar_android_textColorHint, Color.parseColor("#c7c7c7"));
      mSearchEditText.setHintTextColor(textColorHint);
      mCenterSearchView.setHintTextColor(textColorHint);
    }

    //输入文字大小
    if (array.hasValue(R.styleable.PascSearchBar_android_textSize)) {
      int textSize =
              array.getDimensionPixelSize(R.styleable.PascSearchBar_android_textSize, DensityUtils.sp2px(12));
      mSearchEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
      mCenterSearchView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    //输入文字颜色
    if (array.hasValue(R.styleable.PascSearchBar_android_textColor)) {
      int textColor =
              array.getColor(R.styleable.PascSearchBar_android_textColor, Color.parseColor("#333333"));
      mSearchEditText.setTextColor(textColor);
      mCenterSearchView.setTextColor(textColor);
    }

    //设置搜索内容
    if (array.hasValue(R.styleable.PascSearchBar_search_content)) {
      setSearchContent(array.getString(R.styleable.PascSearchBar_search_content));
    }

    //设置返回icon
    if (array.hasValue(R.styleable.PascSearchBar_back_icon)) {
      int mBackIconRes =
              array.getResourceId(R.styleable.PascToolbar_back_icon, R.drawable.ic_back_black);
      mCloseButton.setImageResource(mBackIconRes);
    }

    //搜索按钮文字颜色
    mSearchButton.setTextColor(menuTextColor);
    //搜索按钮文字大小
    mSearchButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);

    mCloseButton.setOnClickListener(onClickListener);
    mSearchButton.setOnClickListener(onClickListener);
    mCenterSearchContainer.setOnClickListener(onClickListener);
    mSearchEditText.setEditTextChangeListener(new ClearEditText.EditTextChangeListener() {
      @Override
      public void afterChange(String s) {
        if (mQueryTextListener != null) mQueryTextListener.onQueryTextChange(s);
      }
    });

    //是否有下划线。默认true
    mEnableUnderDivider = array.getBoolean(R.styleable.PascSearchBar_enable_under_divider, true);

    //设置下划线颜色
    int underDividerColor = array.getColor(R.styleable.PascSearchBar_under_divider_color,
            Color.parseColor("#E0E0E0"));
    //下划线高度
    mUnderDividerHeight =
            array.getDimensionPixelSize(R.styleable.PascSearchBar_under_divider_height, 1); //默认1px

    mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mDividerPaint.setColor(underDividerColor);
    mDividerPaint.setStyle(Paint.Style.FILL);
    if (mEnableUnderDivider) {
      setWillNotDraw(false);
    }

    boolean enableCloseButton =
            array.getBoolean(R.styleable.PascSearchBar_enable_close_button, true);
    if (mMode == MODE_EDIT) enableCloseButton(enableCloseButton);

    mSupportTranslucentStatusBar =
            array.getBoolean(R.styleable.PascSearchBar_support_translucent_status_bar, false);

    array.recycle();
  }

  @Override protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    if (mEnableUnderDivider) {
      canvas.drawRect(getLeft(), getHeight() - mUnderDividerHeight, getRight(), getHeight(),
              mDividerPaint);
    }
  }

  @Override
  protected boolean fitSystemWindows(Rect insets) {
    if (mSupportTranslucentStatusBar && Build.VERSION.SDK_INT < 20) {
      ViewGroup.LayoutParams lp = this.getLayoutParams();
      lp.height = mSearchBarHeight + insets.top;
      this.setLayoutParams(lp);
      this.setPadding(insets.left + getPaddingLeft(), insets.top, insets.right + getPaddingRight(),
              0);
    }
    return super.fitSystemWindows(insets);
  }

  @Override
  public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
    if (mSupportTranslucentStatusBar && Build.VERSION.SDK_INT >= 20) {
      ViewGroup.LayoutParams lp = this.getLayoutParams();
      lp.height = mSearchBarHeight + insets.getSystemWindowInsetTop();
      this.setLayoutParams(lp);
      this.setPadding(insets.getSystemWindowInsetLeft() + getPaddingLeft(),
              insets.getSystemWindowInsetTop(),
              insets.getSystemWindowInsetRight() + getPaddingRight(), 0);
    }
    return super.dispatchApplyWindowInsets(insets);
  }

  private void onCloseClicked() {
    if (mCloseListener != null) mCloseListener.onClose(mCloseButton);
  }

  private void onSearchClicked() {
    if (mSearchClickListener != null) mSearchClickListener.onClick(mSearchButton);
  }

  @IntDef({MODE_EDIT, MODE_CLICKABLE})
  @interface Mode {
  }

  /**
   * 关闭按钮点击监听器
   */
  public interface OnCloseListener {
    /**
     * 回调
     *
     * @param closeButton 点击的view
     */
    void onClose(View closeButton);
  }

  /**
   * 输入文字变化监听器
   */
  public interface OnQueryTextListener {
    /**
     * 回调
     *
     * @param newText 文字字符串
     */
    void onQueryTextChange(String newText);
  }

}
