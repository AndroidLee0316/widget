
package com.pasc.lib.widget.tablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.util.PascCollapsingTextHelper;
import com.pasc.lib.widget.util.PascColorHelper;
import com.pasc.lib.widget.util.PascLangHelper;

// todo custom view
// todo gravity
public class PascTabView extends FrameLayout {

    private PascTab mTab;
    private PascCollapsingTextHelper mCollapsingTextHelper;
    private Interpolator mPositionInterpolator;
    private GestureDetector mGestureDetector;
    private Callback mCallback;
    private float mCurrentIconLeft = 0;
    private float mCurrentIconTop = 0;
    private float mCurrentTextLeft = 0;
    private float mCurrentTextTop = 0;
    private float mCurrentIconWidth = 0;
    private float mCurrentIconHeight = 0;
    private float mCurrentTextWidth = 0;
    private float mCurrentTextHeight = 0;

    private float mNormalIconLeft = 0;
    private float mNormalIconTop = 0;
    private float mNormalTextLeft = 0;
    private float mNormalTextTop = 0;
    private float mSelectedIconLeft = 0;
    private float mSelectedIconTop = 0;
    private float mSelectedTextLeft = 0;
    private float mSelectedTextTop = 0;

    private TextView mSignCountView;

    public PascTabView(@NonNull Context context) {
        super(context);
        setWillNotDraw(false);
        mCollapsingTextHelper = new PascCollapsingTextHelper(this, 1f);
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mCallback != null) {
                    mCallback.onDoubleClick(PascTabView.this);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mCallback != null) {
                    mCallback.onClick(PascTabView.this);
                    return false;
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return mCallback != null;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mCallback != null) {
                    mCallback.onLongClick(PascTabView.this);
                }
            }
        });
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setPositionInterpolator(Interpolator positionInterpolator) {
        mPositionInterpolator = positionInterpolator;
        mCollapsingTextHelper.setPositionInterpolator(positionInterpolator);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    public void bind(PascTab tab) {
        mCollapsingTextHelper.setTextSize(tab.normalTextSize, tab.selectedTextSize, false);
        mCollapsingTextHelper.setTextColor(ColorStateList.valueOf(tab.normalColor),
                ColorStateList.valueOf(tab.selectedColor), false);
        mCollapsingTextHelper.setTypeface(tab.normalTypeface, tab.selectedTypeface, false);
        int gravity = Gravity.LEFT | Gravity.TOP;
        mCollapsingTextHelper.setGravity(gravity, gravity, false);
        mCollapsingTextHelper.setText(tab.getText());
        mTab = tab;
        boolean hasRedPoint = mTab.signCount == PascTab.RED_POINT_SIGN_COUNT;
        boolean hasSignCount = mTab.signCount > 0;
        if (hasRedPoint || hasSignCount) {
            ensureSignCountView(getContext());

            LayoutParams signCountLp = (LayoutParams) mSignCountView.getLayoutParams();
            if (hasSignCount) {
                mSignCountView.setText(
                        PascLangHelper.formatNumberToLimitedDigits(mTab.signCount, mTab.signCountDigits));
                int attrDimen = getAttrDimen(getContext(),
                        R.attr.tl_sign_count_view_minSize_with_text);
                mSignCountView.setMinWidth(attrDimen);
                signCountLp.width = LayoutParams.WRAP_CONTENT;
                signCountLp.height = attrDimen;
            } else {
                mSignCountView.setText(null);
                int redPointSize = getAttrDimen(getContext(),
                        R.attr.tl_sign_count_view_minSize);
                signCountLp.width = redPointSize;
                signCountLp.height = redPointSize;
            }
            mSignCountView.setLayoutParams(signCountLp);
            mSignCountView.setVisibility(View.VISIBLE);
        } else {
            if (mSignCountView != null) {
                mSignCountView.setVisibility(View.GONE);
            }
        }
        requestLayout();
    }

    public static int getAttrDimen(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, DensityUtils.getDisplayMetrics(context));
    }

    public void setSelectFraction(float fraction) {
        fraction = PascLangHelper.constrain(fraction, 0f, 1f);
        PascTabIcon tabIcon = mTab.getTabIcon();
        if (tabIcon != null) {
            tabIcon.setSelectFraction(fraction,
                    PascColorHelper.computeColor(mTab.normalColor, mTab.selectedColor, fraction));
        }
        updateCurrentInfo(fraction);
        mCollapsingTextHelper.setExpansionFraction(1 - fraction);
        if (mSignCountView != null) {
            Point point = calculateSignCountLayoutPosition();
            int x = point.x, y = point.y;
            if (point.x + mSignCountView.getMeasuredWidth() > getMeasuredWidth()) {
                x = getMeasuredWidth() - mSignCountView.getMeasuredWidth();
            }

            if (point.y - mSignCountView.getMeasuredHeight() < 0) {
                y = mSignCountView.getMeasuredHeight();
            }
            ViewCompat.offsetLeftAndRight(mSignCountView, x - mSignCountView.getLeft());
            ViewCompat.offsetTopAndBottom(mSignCountView, y - mSignCountView.getBottom());
        }
    }

    private void updateCurrentInfo(float fraction) {
        mCurrentIconLeft = PascCollapsingTextHelper.lerp(
                mNormalIconLeft, mSelectedIconLeft, fraction, mPositionInterpolator);
        mCurrentIconTop = PascCollapsingTextHelper.lerp(
                mNormalIconTop, mSelectedIconTop, fraction, mPositionInterpolator);
        int normalIconWidth = mTab.getNormalTabIconWidth();
        int normalIconHeight = mTab.getNormalTabIconHeight();
        float selectedScale = mTab.getSelectedTabIconScale();
        mCurrentIconWidth = PascCollapsingTextHelper.lerp(normalIconWidth,
                normalIconWidth * selectedScale, fraction, mPositionInterpolator);
        mCurrentIconHeight = PascCollapsingTextHelper.lerp(normalIconHeight,
                normalIconHeight * selectedScale, fraction, mPositionInterpolator);

        mCurrentTextLeft = PascCollapsingTextHelper.lerp(
                mNormalTextLeft, mSelectedTextLeft, fraction, mPositionInterpolator);
        mCurrentTextTop = PascCollapsingTextHelper.lerp(
                mNormalTextTop, mSelectedTextTop, fraction, mPositionInterpolator);

        float normalTextWidth = mCollapsingTextHelper.getCollapsedTextWidth();
        float normalTextHeight = mCollapsingTextHelper.getCollapsedTextHeight();
        float selectedTextWidth = mCollapsingTextHelper.getExpandedTextWidth();
        float selectedTextHeight = mCollapsingTextHelper.getExpandedTextHeight();
        mCurrentTextWidth = PascCollapsingTextHelper.lerp(
                normalTextWidth, selectedTextWidth, fraction, mPositionInterpolator);
        mCurrentTextHeight = PascCollapsingTextHelper.lerp(
                normalTextHeight, selectedTextHeight, fraction, mPositionInterpolator);
    }

    public int getContentViewWidth() {
        if (mTab == null) {
            return 0;
        }
        float textWidth = mCollapsingTextHelper.getExpandedTextWidth();
        if (mTab.getTabIcon() == null) {
            return (int) (textWidth + 0.5);
        }
        int iconPosition = mTab.getIconPosition();
        float iconWidth = mTab.getNormalTabIconWidth() * mTab.getSelectedTabIconScale();
        if (iconPosition == PascTab.ICON_POSITION_BOTTOM || iconPosition == PascTab.ICON_POSITION_TOP) {
            return (int) (Math.max(iconWidth, textWidth) + 0.5);
        }
        return (int) (iconWidth + textWidth + mTab.getIconTextGap() + 0.5);
    }

    public int getContentViewLeft() {
        if (mTab == null) {
            return 0;
        }
        if (mTab.getTabIcon() == null) {
            return (int) (mSelectedTextLeft + 0.5);
        }
        int iconPosition = mTab.getIconPosition();
        if (iconPosition == PascTab.ICON_POSITION_BOTTOM || iconPosition == PascTab.ICON_POSITION_TOP) {
            return (int) Math.min(mSelectedTextLeft, mSelectedIconLeft + 0.5);
        } else if (iconPosition == PascTab.ICON_POSITION_LEFT) {
            return (int) (mSelectedIconLeft + 0.5);
        } else {
            return (int) (mSelectedTextLeft + 0.5);
        }
    }

    private TextView ensureSignCountView(Context context) {
        if (mSignCountView == null) {
            mSignCountView = createSignCountView(context);
            LayoutParams signCountLp;
            if (mSignCountView.getLayoutParams() != null) {
                signCountLp = new LayoutParams(mSignCountView.getLayoutParams());
            } else {
                signCountLp = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            addView(mSignCountView, signCountLp);
        }
        return mSignCountView;
    }

    protected TextView createSignCountView(Context context) {
        return new TextView(context, null, R.attr.tl_sign_count_view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mTab == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        onMeasureTab(widthSize, heightSize);
        int useWidthMeasureSpec = widthMeasureSpec;
        int useHeightMeasureSpec = heightMeasureSpec;
        PascTabIcon icon = mTab.getTabIcon();
        int iconPosition = mTab.getIconPosition();
        if (widthMode == MeasureSpec.AT_MOST) {
            if (icon == null) {
                widthSize = (int) mCollapsingTextHelper.getExpandedTextWidth();
            } else if (iconPosition == PascTab.ICON_POSITION_BOTTOM ||
                    iconPosition == PascTab.ICON_POSITION_TOP) {
                widthSize = (int) Math.max(
                        mTab.getNormalTabIconWidth() * mTab.getSelectedTabIconScale(),
                        mCollapsingTextHelper.getExpandedTextWidth());
            } else {
                widthSize = (int) (mCollapsingTextHelper.getExpandedTextWidth() +
                        mTab.getIconTextGap() +
                        mTab.getNormalTabIconWidth() * mTab.getSelectedTabIconScale());
            }
            useWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            if (icon == null) {
                heightSize = (int) mCollapsingTextHelper.getExpandedTextHeight();
            } else if (iconPosition == PascTab.ICON_POSITION_LEFT ||
                    iconPosition == PascTab.ICON_POSITION_RIGHT) {
                heightSize = (int) Math.max(
                        mTab.getNormalTabIconHeight() * mTab.getSelectedTabIconScale(),
                        mCollapsingTextHelper.getExpandedTextWidth());
            } else {
                heightSize = (int) (mCollapsingTextHelper.getExpandedTextHeight() +
                        mTab.getIconTextGap() +
                        mTab.getNormalTabIconHeight() * mTab.getSelectedTabIconScale());
            }
            useHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(useWidthMeasureSpec, useHeightMeasureSpec);
    }

    protected void onMeasureTab(int widthSize, int heightSize) {
        int textWidth = widthSize, textHeight = heightSize;
        PascTabIcon icon = mTab.getTabIcon();
        if (icon != null && !mTab.isAllowIconDrawOutside()) {
            float iconWidth = mTab.getNormalTabIconWidth() * mTab.selectedTabIconScale;
            float iconHeight = mTab.getNormalTabIconHeight() * mTab.selectedTabIconScale;
            int iconPosition = mTab.iconPosition;
            if (iconPosition == PascTab.ICON_POSITION_TOP || iconPosition == PascTab.ICON_POSITION_BOTTOM) {
                textHeight -= iconHeight - mTab.getIconTextGap();
            } else {
                textWidth -= iconWidth - mTab.getIconTextGap();
            }
        }
        mCollapsingTextHelper.setCollapsedBounds(0, 0, textWidth, textHeight);
        mCollapsingTextHelper.setExpandedBounds(0, 0, textWidth, textHeight);
        mCollapsingTextHelper.calculateBaseOffsets();
    }

    @Override
    protected final void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        onLayoutTab(right - left, bottom - top);
        onLayoutSignCount(right - left, bottom - top);
    }

    protected void onLayoutSignCount(int width, int height) {
        if (mSignCountView != null && mTab != null) {
            Point point = calculateSignCountLayoutPosition();
            int x = point.x, y = point.y;
            if (point.x + mSignCountView.getMeasuredWidth() > width) {
                x = width - mSignCountView.getMeasuredWidth();
            }

            if (point.y - mSignCountView.getMeasuredHeight() < 0) {
                y = mSignCountView.getMeasuredHeight();
            }
            mSignCountView.layout(x, y - mSignCountView.getMeasuredHeight(),
                    x + mSignCountView.getMeasuredWidth(), y);
        }
    }

    private Point calculateSignCountLayoutPosition() {
        PascTabIcon icon = mTab.getTabIcon();
        int left, bottom;
        int iconPosition = mTab.getIconPosition();
        if (icon == null || iconPosition == PascTab.ICON_POSITION_BOTTOM ||
                iconPosition == PascTab.ICON_POSITION_LEFT) {
            left = (int) (mCurrentTextLeft + mCurrentTextWidth);
            bottom = (int) (mCurrentTextTop);
        } else {
            left = (int) (mCurrentIconLeft + mCurrentIconWidth);
            bottom = (int) (mCurrentIconTop);
        }
        Point point = new Point(left, bottom);
        point.offset(mTab.signCountLeftMarginWithIconOrText, mTab.signCountBottomMarginWithIconOrText);
        return point;
    }

    protected void onLayoutTab(int width, int height) {
        if (mTab == null) {
            return;
        }
        mCollapsingTextHelper.calculateCurrentOffsets();
        PascTabIcon icon = mTab.getTabIcon();
        float normalTextWidth = mCollapsingTextHelper.getCollapsedTextWidth();
        float normalTextHeight = mCollapsingTextHelper.getCollapsedTextHeight();

        float selectedTextWidth = mCollapsingTextHelper.getExpandedTextWidth();
        float selectedTextHeight = mCollapsingTextHelper.getExpandedTextHeight();

        if (icon == null) {
            mNormalIconLeft = mNormalIconTop = mSelectedIconLeft = mSelectedIconTop = 0;
            switch (mTab.gravity & Gravity.VERTICAL_GRAVITY_MASK) {
                case Gravity.BOTTOM:
                    mNormalTextTop = height - normalTextHeight;
                    mSelectedTextTop = height - selectedTextHeight;
                    break;
                case Gravity.TOP:
                    mNormalTextTop = 0;
                    mSelectedTextTop = 0;
                    break;
                case Gravity.CENTER_VERTICAL:
                default:
                    mNormalTextTop = (height - normalTextHeight) / 2;
                    mSelectedTextTop = (height - selectedTextHeight) / 2;
                    break;
            }

            switch (mTab.gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                case Gravity.RIGHT:
                    mNormalTextLeft = width - normalTextWidth;
                    mSelectedTextLeft = width - selectedTextWidth;
                    break;
                case Gravity.LEFT:
                    mNormalTextLeft = 0;
                    mSelectedTextLeft = 0;
                    break;
                case Gravity.CENTER_HORIZONTAL:
                default:
                    mNormalTextLeft = (width - normalTextWidth) / 2;
                    mSelectedTextLeft = (width - selectedTextWidth) / 2;
                    break;
            }
        } else {
            int gap = mTab.getIconTextGap();
            int iconPosition = mTab.iconPosition;

            // icon
            float normalIconWidth = mTab.getNormalTabIconWidth();
            float normalIconHeight = mTab.getNormalTabIconHeight();
            float selectedIconWidth = normalIconWidth * mTab.getSelectedTabIconScale();
            float selectedIconHeight = normalIconHeight * mTab.getSelectedTabIconScale();

            // total size
            float normalTotalWidth = normalTextWidth + gap + normalIconWidth;
            float normalTotalHeight = normalTextHeight + gap + normalIconHeight;
            float selectedTotalWidth = selectedTextWidth + gap + selectedIconWidth;
            float selectedTotalHeight = selectedTextHeight + gap + selectedIconHeight;

            if (iconPosition == PascTab.ICON_POSITION_TOP || iconPosition == PascTab.ICON_POSITION_BOTTOM) {
                switch (mTab.gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.RIGHT:
                        mNormalIconLeft = width - normalIconWidth;
                        mNormalTextLeft = width - normalTextWidth;
                        mSelectedIconLeft = width - selectedIconWidth;
                        mSelectedTextLeft = width - selectedTextWidth;
                        break;
                    case Gravity.LEFT:
                        mNormalIconLeft = 0;
                        mNormalTextLeft = 0;
                        mSelectedIconLeft = 0;
                        mSelectedTextLeft = 0;
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                    default:
                        mNormalIconLeft = (width - normalIconWidth) / 2;
                        mNormalTextLeft = (width - normalTextWidth) / 2;
                        mSelectedIconLeft = (width - selectedIconWidth) / 2;
                        mSelectedTextLeft = (width - selectedTextWidth) / 2;
                        break;
                }

                switch (mTab.gravity & Gravity.VERTICAL_GRAVITY_MASK) {
                    case Gravity.BOTTOM:
                        if (iconPosition == PascTab.ICON_POSITION_TOP) {
                            mNormalTextTop = height - normalTextHeight;
                            mSelectedTextTop = height - selectedTextHeight;
                            mNormalIconTop = mNormalTextTop - gap - normalIconHeight;
                            mSelectedIconTop = mSelectedTextTop - gap - selectedIconHeight;
                        } else {
                            mNormalIconTop = height - normalIconHeight;
                            mSelectedIconTop = height - selectedIconHeight;
                            mNormalTextTop = mNormalIconTop - gap - normalTextHeight;
                            mSelectedTextTop = mSelectedIconTop - gap - selectedTextHeight;
                        }
                        break;
                    case Gravity.TOP:
                        if (iconPosition == PascTab.ICON_POSITION_TOP) {
                            mNormalIconTop = 0;
                            mSelectedIconTop = 0;
                            mNormalTextTop = normalIconHeight + gap;
                            mSelectedTextTop = selectedIconHeight + gap;
                        } else {
                            mNormalTextTop = 0;
                            mSelectedTextTop = 0;
                            mNormalIconTop = normalTextHeight + gap;
                            mSelectedIconTop = selectedTextHeight + gap;
                        }
                        break;
                    case Gravity.CENTER_VERTICAL:
                    default:
                        // if the space is not enough, keep text
                        if (iconPosition == PascTab.ICON_POSITION_TOP) {
                            // normal
                            if (normalTotalHeight >= height) {
                                mNormalIconTop = height - normalTotalHeight;
                            } else {
                                mNormalIconTop = (height - normalTotalHeight) / 2;
                            }
                            mNormalTextTop = mNormalIconTop + gap + normalIconHeight;

                            // selected
                            if (selectedTotalHeight >= height) {
                                mSelectedIconTop = height - selectedTotalHeight;
                            } else {
                                mSelectedIconTop = (height - selectedTotalHeight) / 2;
                            }
                            mSelectedTextTop = mSelectedIconTop + gap + selectedIconHeight;
                        } else {
                            // normal
                            if (normalTotalHeight >= height) {
                                mNormalTextTop = 0;
                            } else {
                                mNormalTextTop = (height - normalTotalHeight) / 2;
                            }
                            mNormalIconTop = mNormalTextTop + gap + normalTextHeight;

                            // selected
                            if (selectedTotalHeight >= height) {
                                mNormalTextTop = 0;
                            } else {
                                mNormalTextTop = (height - selectedTotalHeight) / 2;
                            }
                            mNormalIconTop = mNormalTextTop + gap + selectedTextHeight;
                        }
                        break;
                }
            } else {
                switch (mTab.gravity & Gravity.VERTICAL_GRAVITY_MASK) {
                    case Gravity.BOTTOM:
                        mNormalIconTop = height - normalIconHeight;
                        mNormalTextTop = height - normalTextHeight;
                        mSelectedIconTop = height - selectedIconHeight;
                        mSelectedTextTop = height - selectedTextHeight;
                        break;
                    case Gravity.TOP:
                        mNormalIconTop = 0;
                        mNormalTextTop = 0;
                        mSelectedIconTop = 0;
                        mSelectedTextTop = 0;
                        break;
                    case Gravity.CENTER_VERTICAL:
                    default:
                        mNormalIconTop = (height - normalIconHeight) / 2;
                        mNormalTextTop = (height - normalTextHeight) / 2;
                        mSelectedIconTop = (height - selectedIconHeight) / 2;
                        mSelectedTextTop = (height - selectedTextHeight) / 2;
                        break;
                }

                switch (mTab.gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.RIGHT:
                        if (iconPosition == PascTab.ICON_POSITION_RIGHT) {
                            mNormalTextLeft = width - normalTotalWidth;
                            mSelectedTextLeft = width - selectedTotalWidth;
                            mNormalIconLeft = width - normalIconWidth;
                            mSelectedIconLeft = width - selectedIconWidth;
                        } else {
                            mNormalIconLeft = width - normalTotalWidth;
                            mSelectedIconLeft = width - selectedTotalWidth;
                            mNormalTextLeft = width - normalTextWidth;
                            mSelectedTextLeft = width - selectedTextWidth;
                        }
                        break;
                    case Gravity.LEFT:
                        if (iconPosition == PascTab.ICON_POSITION_RIGHT) {
                            mNormalTextLeft = 0;
                            mSelectedTextLeft = 0;
                            mNormalIconLeft = normalTextWidth + gap;
                            mSelectedIconLeft = selectedTextWidth + gap;
                        } else {
                            mNormalIconLeft = 0;
                            mSelectedIconLeft = 0;
                            mNormalTextLeft = normalIconWidth + gap;
                            mSelectedTextLeft = selectedIconWidth + gap;
                        }
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                    default:
                        if (iconPosition == PascTab.ICON_POSITION_RIGHT) {
                            mNormalTextLeft = (width - normalTotalWidth) / 2;
                            mSelectedTextLeft = (width - selectedTotalWidth) / 2;
                            mNormalIconLeft = mNormalTextLeft + normalTextWidth + gap;
                            mSelectedIconLeft = mSelectedTextLeft + selectedTextWidth + gap;
                        } else {
                            mNormalIconLeft = (width - normalTotalWidth) / 2;
                            mSelectedIconLeft = (width - selectedTotalWidth) / 2;
                            mNormalTextLeft = mNormalIconLeft + normalIconWidth + gap;
                            mSelectedTextLeft = mSelectedIconLeft + selectedIconWidth + gap;
                        }
                        break;
                }

                if (iconPosition == PascTab.ICON_POSITION_LEFT) {
                    // normal
                    if (normalTotalWidth >= width) {
                        mNormalIconLeft = width - normalTotalWidth;
                    } else {
                        mNormalIconLeft = (width - normalTotalWidth) / 2;
                    }
                    mNormalTextLeft = mNormalIconLeft + normalIconWidth + gap;

                    // selected
                    if (selectedTotalWidth >= width) {
                        mSelectedIconLeft = width - selectedTotalWidth;
                    } else {
                        mSelectedIconLeft = (width - selectedTotalWidth) / 2;
                    }
                    mSelectedTextLeft = mSelectedIconLeft + selectedIconWidth + gap;
                } else {
                    // normal
                    if (normalTotalWidth >= width) {
                        mNormalTextLeft = 0;
                    } else {
                        mNormalTextLeft = (width - normalTotalWidth) / 2;
                    }
                    mNormalIconLeft = mNormalTextLeft + normalTextWidth + gap;

                    // selected
                    if (selectedTotalWidth >= width) {
                        mSelectedTextLeft = 0;
                    } else {
                        mSelectedTextLeft = (width - selectedTotalWidth) / 2;
                    }
                    mSelectedIconLeft = mSelectedTextLeft + selectedTextWidth + gap;
                }
            }
        }
        updateCurrentInfo(1 - mCollapsingTextHelper.getExpansionFraction());
    }

    @Override
    public final void draw(Canvas canvas) {
        onDrawTab(canvas);
        super.draw(canvas);
    }

    protected void onDrawTab(Canvas canvas) {
        if (mTab == null) {
            return;
        }
        PascTabIcon icon = mTab.getTabIcon();
        if (icon != null) {
            canvas.save();
            canvas.translate(mCurrentIconLeft, mCurrentIconTop);
            icon.setBounds(0, 0, (int) mCurrentIconWidth, (int) mCurrentIconHeight);
            icon.draw(canvas);
            canvas.restore();
        }

        canvas.save();
        canvas.translate(mCurrentTextLeft, mCurrentTextTop);
        mCollapsingTextHelper.draw(canvas);
        canvas.restore();
    }

    public interface Callback {
        void onClick(PascTabView view);

        void onDoubleClick(PascTabView view);

        void onLongClick(PascTabView view);
    }
}
