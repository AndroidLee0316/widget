package com.pasc.lib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 通用Title View
 * Created by duyuan797 on 17/3/16.
 */
@Deprecated
public class Toolbar extends LinearLayout {

    private static final String TAG = Toolbar.class.getSimpleName();

    protected View view;

    protected ImageView mLeftIv;

    protected TextView mRightTV; // 返回

    protected TextView mTitleTV; // 标题

    protected TextView mLeftTV; // 下一个
    protected ImageView mRightIv;  //右图标
    protected ImageView mRightLeftIv;
    protected View vDivider; //分隔线
    private ProgressBar mProgressBar;

    private RelativeLayout rlFirst;

    private TranslateAnimation mShowAction, mHiddenAction;

    private OnClickListener mBackClickListener; //返回按钮监听事件

    public Toolbar(Context context) {
        super(context);
        initView(context, null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {
        // 设置背景
        setBackgroundColor(getResources().getColor(R.color.title_bar));

        view = LayoutInflater.from(context).inflate(R.layout.toolbar, null);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view, params);

        mRightTV = (TextView) view.findViewById(R.id.common_title_right);
        mTitleTV = (TextView) view.findViewById(R.id.common_title_name);
        mLeftTV = (TextView) view.findViewById(R.id.common_title_left);
        mLeftIv = (ImageView) view.findViewById(R.id.iv_title_left);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mRightIv = (ImageView) view.findViewById(R.id.iv_title_Right);
        mRightLeftIv = (ImageView) view.findViewById(R.id.iv_title_right_left);
        rlFirst = (RelativeLayout) view.findViewById(R.id.rl_first);
        vDivider = (View) view.findViewById(R.id.v_title_devider);
        if (attrs != null) {

            //获得这个控件对应的属性。
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Toolbar);

            try {
                //获得属性值
                //getColor(R.styleable.commonTitle_RxBackground, getResources().getColor(R.color.transparent))
                String title = a.getString(R.styleable.Toolbar_title);//标题
                int titleColor =
                        a.getColor(R.styleable.Toolbar_titleColor, getResources().getColor(R.color.toolbar_title_color));//标题颜色
                int bgColor = a.getColor(R.styleable.Toolbar_backgroundColor,
                        getResources().getColor(R.color.title_bar));//标题颜色
                setBackgroundColor(bgColor);
                int titleSize = a.getDimensionPixelSize(R.styleable.Toolbar_titleSize,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 19,
                                getResources().getDisplayMetrics()));
                boolean titleVisibility =
                        a.getBoolean(R.styleable.Toolbar_titleVisibility, true);
                mTitleTV.setText(title);
                mTitleTV.setTextColor(titleColor);
                mTitleTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
                mTitleTV.setVisibility(titleVisibility ? VISIBLE : GONE);

                int leftIcon = a.getResourceId(R.styleable.Toolbar_leftIcon,
                        R.drawable.ic_back_blue);//左边图标
                boolean leftIconVisibility =
                        a.getBoolean(R.styleable.Toolbar_leftIconVisibility,
                                true);//左边图标是否显示
                mLeftIv.setImageResource(leftIcon);
                mLeftIv.setVisibility(leftIconVisibility ? VISIBLE : GONE);

                int rightIcon = a.getResourceId(R.styleable.Toolbar_rightIcon,
                        R.mipmap.ic_launcher);//右边图标
                boolean rightIconVisibility =
                        a.getBoolean(R.styleable.Toolbar_rightIconVisibility,
                                false);//右边图标是否显示
                mRightIv.setImageResource(rightIcon);
                mRightIv.setVisibility(rightIconVisibility ? VISIBLE : GONE);

                int rightLeftIcon = a.getResourceId(R.styleable.Toolbar_rightLeftIcon,
                        R.mipmap.ic_launcher);//右边往左第二个图标
                boolean rightLeftIconVisibility =
                        a.getBoolean(R.styleable.Toolbar_rightLeftIconVisibility,
                                false);//右边右边往左第二个图标是否显示
                mRightLeftIv.setImageResource(rightLeftIcon);
                mRightLeftIv.setVisibility(rightLeftIconVisibility ? VISIBLE : GONE);

                String leftText = a.getString(R.styleable.Toolbar_leftText);
                int leftTextColor =
                        a.getColor(R.styleable.Toolbar_leftTextColor, Color.WHITE);//左边字体颜色
                int leftTextSize = a.getDimensionPixelSize(R.styleable.Toolbar_leftTextSize,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                                getResources().getDisplayMetrics()));
                boolean leftTextVisibility =
                        a.getBoolean(R.styleable.Toolbar_leftTextVisibility, false);
                mLeftTV.setText(leftText);
                mLeftTV.setTextColor(leftTextColor);
                mLeftTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
                mLeftTV.setVisibility(leftTextVisibility ? VISIBLE : GONE);

                String rightText = a.getString(R.styleable.Toolbar_rightText);
                int rightTextColor =
                        a.getColor(R.styleable.Toolbar_rightTextColor, getResources().getColor(R.color.toolbar_right_text_color));//右边字体颜色
                int rightTextSize =
                        a.getDimensionPixelSize(R.styleable.Toolbar_rightTextSize,
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15,
                                        getResources().getDisplayMetrics()));
                boolean rightTextVisibility =
                        a.getBoolean(R.styleable.Toolbar_rightTextVisibility, false);
                mRightTV.setText(rightText);
                mRightTV.setTextColor(rightTextColor);
                mRightTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
                mRightTV.setVisibility(rightTextVisibility ? VISIBLE : GONE);

                boolean dividerVisibility =
                        a.getBoolean(R.styleable.Toolbar_titleDividerVisibility, false);
                setDividerVisible(dividerVisibility);

                setListener();
            } finally {
                //回收这个对象
                a.recycle();
            }
        }

        mShowAction =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                        0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        mHiddenAction =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                        0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(500);
    }

    //设置监听
    private void setListener() {
        mLeftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackClickListener != null) {
                    mBackClickListener.onClick(v);
                } else {
                    Context context = getContext();
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.onBackPressed();
                    }
                }
            }
        });
    }

    /**
     * 设置顶部分割线是否隐藏
     *
     * @param visible true:显示，false:隐藏
     */
    public void setDividerVisible(boolean visible) {
        vDivider.setVisibility(visible ? VISIBLE : GONE);
    }

    /**
     * 是否显示阴影
     */
    public Toolbar setShadowVisible(boolean isShow) {
        setBackgroundResource(isShow ? R.drawable.common_title_bg : R.color.white);
        return this;
    }

    public Toolbar setTopRelBackGround(int resId) {
        rlFirst.setBackgroundResource(resId);
        return this;
    }

    public Toolbar setOnLeftClickListener(OnClickListener clickListener) {
        mBackClickListener = clickListener;
        return this;
    }

    public Toolbar setOnLeftTextClickListener(OnClickListener clickListener) {
        mLeftTV.setOnClickListener(clickListener);
        return this;
    }

    public Toolbar setOnTitleClickListener(OnClickListener clickListener) {
        mTitleTV.setOnClickListener(clickListener);
        return this;
    }

    public Toolbar setOnRightClickListener(OnClickListener clickListener) {
        mRightTV.setOnClickListener(clickListener);
        return this;
    }

    public Toolbar setOnRightImageClickListener(OnClickListener clickListener) {
        mRightIv.setOnClickListener(clickListener);
        return this;
    }

    public Toolbar setOnRightLeftImageDrawable(int resId) {
        mRightLeftIv.setVisibility(VISIBLE);
        mRightLeftIv.setImageResource(resId);
        return this;
    }

    public Toolbar setOnRightLeftImageClickListener(OnClickListener clickListener) {
        mRightLeftIv.setOnClickListener(clickListener);
        return this;
    }

    public Toolbar setOnRightLeftImageVisible(int visible) {
        mRightLeftIv.setVisibility(visible);
        return this;
    }

    /**
     * 右边往左第二个按钮是否隐藏
     */
    public boolean getRightLeftIconVisibility() {
        return mRightLeftIv.getVisibility() == VISIBLE;
    }

    public Toolbar setLeftText(String text) {
        mLeftTV.setVisibility(View.VISIBLE);
        mLeftTV.setText(text);
        mLeftTV.setVisibility(View.VISIBLE);
        return this;
    }

    public Toolbar setBgColor(@ColorInt int colorId) {
        rlFirst.setBackgroundColor(colorId);
        return this;
    }

    public void setLeftTextColor(int color) {
        mLeftTV.setTextColor(color);
    }

    public Toolbar setLeftText(int text) {
        mLeftTV.setVisibility(View.VISIBLE);
        mLeftTV.setText(text);
        return this;
    }

    public Toolbar setTitleText(String text) {
        if (!TextUtils.isEmpty(text) && text.length() > 14) {
            text = text.substring(0, 13) + "...";
        }
        mTitleTV.setText(text);
        return this;
    }

    public void setTitleTextColor(@ColorRes int colorRes) {
        mTitleTV.setTextColor(getContext().getResources().getColor(colorRes));
    }

    public void setTitleWeight(float weight) {
        LayoutParams params =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.weight = weight;
        params.gravity = Gravity.CENTER_VERTICAL;
        mTitleTV.setLayoutParams(params);
    }

    public Toolbar setTitleText(int text) {
        mTitleTV.setText(text);
        return this;
    }

    public Toolbar setRightText(String text) {
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setText(text);
        return this;
    }

    public Toolbar setRightText(int text) {
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setText(text);
        return this;
    }

    public void setRightTextColor(int rid) {
        mRightTV.setTextColor(rid);
    }

    public void setRightTextSize(int size) {
        mRightTV.setTextSize(size);
    }

    public Toolbar setRightTextVisibility(int visiable) {
        mRightTV.setVisibility(visiable);
        return this;
    }

    public void setLeftTextVisibility(int visiable) {
        mLeftTV.setVisibility(visiable);
    }

    public Toolbar setBackDrawableLeft(int rId) {
        if (rId != 0) {
            mLeftIv.setVisibility(View.VISIBLE);
            mLeftIv.setImageResource(rId);
        }
        return this;
    }

    public Toolbar setBackDrawableVisible(int visible) {
        mLeftIv.setVisibility(visible);
        return this;
    }

    public Toolbar setRightImageVisible(int visible) {
        mRightIv.setVisibility(visible);
        return this;
    }

    public Toolbar setRightDrawableRight(int rId) {
        if (rId != 0) {
            mRightIv.setVisibility(View.VISIBLE);
            mRightIv.setImageResource(rId);
        }
        return this;
    }

    public void setTitleDrawableRight(int rId) {
        if (rId > 0) {
            Drawable drawable = getResources().getDrawable(rId);
            mTitleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            mTitleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    public void setNextDrawableRight(int rId) {
        if (rId != 0) {
            mRightTV.setVisibility(View.VISIBLE);
            Drawable drawable = getResources().getDrawable(rId);
            mRightTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            mTitleTV.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void dismissLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    public ImageView getLeftIv() {
        return mLeftIv;
    }

    public TextView getRightTV() {
        return mRightTV;
    }

    public TextView getTitleTV() {
        return mTitleTV;
    }

    public TextView getLeftTV() {
        return mLeftTV;
    }

    public ImageView getRightIv() {
        return mRightIv;
    }

    public CharSequence getTitle() {
        return mTitleTV.getText();
    }
}
