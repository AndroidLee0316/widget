package com.pasc.lib.widget.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;

@SuppressLint("AppCompatCustomView")
public class PascLoadingButton extends FrameLayout {
    private static final int TEXTVIEW_LEFT_MARGIN_DEFAULT = 10;

    /** 文字按钮 */
    private TextView mTextTv;
    /** Loading图标按钮 */
    private ImageView mLoadingIv;
    /** 跟布局 */
    private FrameLayout mContainer;

    /** 文字字符串 */
    private CharSequence mText;
    /** 文字大小 */
    private float mTextSize;
    /** 文字颜色 */
    private int mTextColor;
    /** 控件背景 */
    private int mBackground;

    public PascLoadingButton(Context context) {
        this(context, null);
    }

    public PascLoadingButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.PascLoadingButtonStyle);
    }

    public PascLoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View rootView = LayoutInflater.from(context).inflate(R.layout.pasc_loading_button, this, true);
        initView(rootView);
        int defaultTextColor = getResources().getColor(R.color.white);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PascLoadingButton);
        mText = typedArray.getString(R.styleable.PascLoadingButton_text);
        mTextSize = typedArray.getDimension(R.styleable.PascLoadingButton_text_size, DensityUtils.sp2px(17));
        mTextColor = typedArray.getColor(R.styleable.PascLoadingButton_text_color, defaultTextColor);
        int pressedTextColor = typedArray.getColor(R.styleable.PascLoadingButton_pressed_text_color, mTextColor);
        mBackground = typedArray.getResourceId(R.styleable.PascLoadingButton_background, R.drawable.primary_button_bg);
        Drawable loadingDrawable = typedArray.getDrawable(R.styleable.PascLoadingButton_loading_img);
        boolean enabled = typedArray.getBoolean(R.styleable.PascLoadingButton_enabled, true);
        setEnabled(enabled);
        if (loadingDrawable != null) {
            setLoadingDrawable(loadingDrawable);
        }
        setBackground(mBackground);
        setText(mText);
        setTextSize(DensityUtils.px2sp(mTextSize));
        setTextColor(mTextColor);
        mLoadingIv.setVisibility(GONE);
        mTextTv.setTextColor(createColorStateList(mTextColor, pressedTextColor, pressedTextColor, mTextColor));
        refreshViewState();

        typedArray.recycle();
    }

    private void initView(View rootView) {
        mContainer = rootView.findViewById(R.id.lin_background);
        mTextTv = rootView.findViewById(R.id.tv_content);
        mLoadingIv = rootView.findViewById(R.id.img);
    }

    /**
     * 根据不同状态设置的颜色创建ColorStateList
     *
     * @param normalColor  正常状态的颜色
     * @param focusedColor 获取到焦点时的颜色
     * @param pressedColor 按压状态时的颜色
     * @param unableColor  不可点击时的颜色
     */
    private ColorStateList createColorStateList(int normalColor, int pressedColor, int focusedColor, int unableColor) {
        int[] colors = new int[]{pressedColor, focusedColor, normalColor, focusedColor, unableColor, normalColor};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_focused, android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }

    /**
     * 设置文字
     *
     * @param text 文字字符
     */
    public void setText(CharSequence text) {
        mText = text;
        if (TextUtils.isEmpty(text)) {
            mTextTv.setVisibility(GONE);
        } else {
            mTextTv.setVisibility(VISIBLE);
            mTextTv.setText(text);
        }
        refreshViewState();
    }

    /**
     * 设置文字颜色
     *
     * @param color 颜色
     */
    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
        mTextTv.setTextColor(color);
    }


    /**
     * 设置字体大小
     *
     * @param textSize 字体大小，单位：sp
     */
    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mTextTv.setTextSize(textSize);
    }

    /**
     * 设置文字是否隐藏
     *
     * @param visible true:显示，false:隐藏
     */
    public void setTextVisible(boolean visible) {
        if (visible) {
            mTextTv.setVisibility(VISIBLE);
        } else {
            mTextTv.setVisibility(GONE);
        }
        refreshViewState();
    }

    /**
     * 设置控件背景
     *
     * @param background 控件背景资源
     */
    public void setBackground(@DrawableRes int background) {
        mBackground = background;
        mContainer.setBackgroundResource(background);
    }

    /**
     * 设置Loading资源
     *
     * @param drawable loading图片资源
     */
    public void setLoadingDrawable(Drawable drawable) {
        if (drawable != null) {
            mLoadingIv.setVisibility(VISIBLE);
            mLoadingIv.setImageDrawable(drawable);
        } else {
            mLoadingIv.setVisibility(GONE);
        }
        refreshViewState();
    }

    /**
     * 设置控件是否可以被点击
     *
     * @param enabled true:可以点击，false:不可点击
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mTextTv.setEnabled(enabled);
        mLoadingIv.setEnabled(enabled);
    }

    private void setTextMarginLeft(TextView textView, int leftMargin) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.leftMargin = DensityUtils.dip2px(getContext(), leftMargin);
        textView.setLayoutParams(layoutParams);
    }

    public void startLoading() {
        setEnabled(false);
        mLoadingIv.setVisibility(VISIBLE);
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        mLoadingIv.startAnimation(animation);
        refreshViewState();
    }

    public void stopLoading() {
        setEnabled(true);
        mLoadingIv.setVisibility(GONE);
        mLoadingIv.clearAnimation();
        refreshViewState();
    }


    private void refreshViewState() {
        int visibilityForTextTv = mTextTv.getVisibility();
        int visibilityForLoadingTv = mLoadingIv.getVisibility();
        if (visibilityForTextTv == VISIBLE && visibilityForLoadingTv == VISIBLE) {
            setTextMarginLeft(mTextTv, TEXTVIEW_LEFT_MARGIN_DEFAULT);
        } else if (visibilityForTextTv == VISIBLE && visibilityForLoadingTv == GONE) {
            setTextMarginLeft(mTextTv, 0);
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}
