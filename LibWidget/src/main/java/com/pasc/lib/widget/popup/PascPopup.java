
package com.pasc.lib.widget.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.refreshlayout.util.DensityUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 提供一个浮层，支持自定义浮层的内容，支持在指定 {@link View} 的任一方向旁边展示该浮层，支持自定义浮层出现/消失的动画。
 * Created by chendaixi947 on 2019/4/24
 *
 * @version 1.0
 */
public class PascPopup extends PascBasePopup {
    public static final int ANIM_GROW_FROM_LEFT = 1;
    public static final int ANIM_GROW_FROM_RIGHT = 2;
    public static final int ANIM_GROW_FROM_CENTER = 3;
    public static final int ANIM_AUTO = 4;

    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_NONE = 2;

    private ImageView mArrowUp;
    private ImageView mArrowDown;
    protected int mAnimStyle;
    private int mDirection;
    private int mX = -1;
    private int mY = -1;
    private int mArrowCenter;
    // 该PopupWindow的View距离屏幕左右的最小距离
    private int mPopupLeftRightMinMargin = 0;
    // 该PopupWindow的View距离屏幕上下的最小距离
    private int mPopupTopBottomMinMargin = 0;
    private int mPreferredDirection;
    // 计算位置后的偏移x值
    private int mOffsetX = 0;
    // 计算位置后的偏移y值，当浮层在View的上方时使用
    private int mOffsetYWhenTop = 0;
    // 计算位置后的偏移y值，当浮层在View的下方时使用
    private int mOffsetYWhenBottom = 0;

    protected BaseAdapter mAdapter;
    protected int mListWidth;
    protected int mListMaxHeight;

    PascPopup(Context context) {
        this(context, DIRECTION_NONE);
    }

    private PascPopup(Context context, @Direction int preferredDirection) {
        super(context);
        mAnimStyle = ANIM_AUTO;
        mPreferredDirection = preferredDirection;
        mDirection = mPreferredDirection;
    }

    /**
     * 设置ListPopup的item点击监听
     *
     * @param onItemClickListener item点击监听回调
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {

    }

    @Override
    protected Point onShowBegin(@NonNull View parent, @NonNull View attachedView) {
        calculatePosition(attachedView);
        showArrow();
        setAnimationStyle(mScreenSize.x, mArrowCenter);
        int offsetY = 0;
        if (mDirection == DIRECTION_TOP) {
            offsetY = mOffsetYWhenTop;
        } else if (mDirection == DIRECTION_BOTTOM) {
            offsetY = mOffsetYWhenBottom;
        }
        return new Point(mX + mOffsetX, mY + offsetY);
    }

    private void calculatePosition(View attachedView) {
        if (attachedView != null) {
            int[] attachedViewLocation = new int[2];
            attachedView.getLocationOnScreen(attachedViewLocation);
            mArrowCenter = attachedViewLocation[0] + attachedView.getWidth() / 2;
            if (mArrowCenter < mScreenSize.x / 2) {//描点在左侧
                if (mArrowCenter - mWindowWidth / 2 > mPopupLeftRightMinMargin) {
                    mX = mArrowCenter - mWindowWidth / 2;
                } else {
                    mX = mPopupLeftRightMinMargin;
                }
            } else {//描点在右侧
                if (mArrowCenter + mWindowWidth / 2 < mScreenSize.x - mPopupLeftRightMinMargin) {
                    mX = mArrowCenter - mWindowWidth / 2;
                } else {
                    mX = mScreenSize.x - mPopupLeftRightMinMargin - mWindowWidth;
                }
            }
            //实际的方向和期望的方向可能不一致，每次都需要重新
            mDirection = mPreferredDirection;
            switch (mPreferredDirection) {
                case DIRECTION_TOP:
                    mY = attachedViewLocation[1] - mWindowHeight;
                    if (mY < mPopupTopBottomMinMargin) {
                        mY = attachedViewLocation[1] + attachedView.getHeight();
                        mDirection = DIRECTION_BOTTOM;
                    }
                    break;
                case DIRECTION_BOTTOM:
                    mY = attachedViewLocation[1] + attachedView.getHeight();
                    if (mY > mScreenSize.y - mPopupTopBottomMinMargin - mWindowHeight) {
                        mY = attachedViewLocation[1] - mWindowHeight;
                        mDirection = DIRECTION_TOP;
                    }
                    break;
                case DIRECTION_NONE:
                    // 默认Y值与attachedView的Y值相同
                    mY = attachedViewLocation[1];
                    break;
            }
        } else {
            mX = (mScreenSize.x - mWindowWidth) / 2;
            mY = (mScreenSize.y - mWindowHeight) / 2;
            mDirection = DIRECTION_NONE;
        }
    }

    /**
     * 设置 animation style
     *
     * @param screenWidth screen width
     * @param requestedX  distance from left edge
     */
    private void setAnimationStyle(int screenWidth, int requestedX) {
        int arrowPos = requestedX;
        if (mArrowUp != null) {
            arrowPos -= mArrowUp.getMeasuredWidth() / 2;
        }
        boolean onTop = mDirection == DIRECTION_TOP;
        switch (mAnimStyle) {
            case ANIM_GROW_FROM_LEFT:
                mPopupWindow.setAnimationStyle(onTop ? R.style.Pasc_Animation_PopUpMenu_Left : R.style.Pasc_Animation_PopDownMenu_Left);
                break;

            case ANIM_GROW_FROM_RIGHT:
                mPopupWindow.setAnimationStyle(onTop ? R.style.Pasc_Animation_PopUpMenu_Right : R.style.Pasc_Animation_PopDownMenu_Right);
                break;

            case ANIM_GROW_FROM_CENTER:
                mPopupWindow.setAnimationStyle(onTop ? R.style.Pasc_Animation_PopUpMenu_Center : R.style.Pasc_Animation_PopDownMenu_Center);
                break;
            case ANIM_AUTO:
                if (arrowPos <= screenWidth / 4) {
                    mPopupWindow.setAnimationStyle(onTop ? R.style.Pasc_Animation_PopUpMenu_Left : R.style.Pasc_Animation_PopDownMenu_Left);
                } else if (arrowPos > screenWidth / 4 && arrowPos < 3 * (screenWidth / 4)) {
                    mPopupWindow.setAnimationStyle(onTop ? R.style.Pasc_Animation_PopUpMenu_Center : R.style.Pasc_Animation_PopDownMenu_Center);
                } else {
                    mPopupWindow.setAnimationStyle(onTop ? R.style.Pasc_Animation_PopUpMenu_Right : R.style.Pasc_Animation_PopDownMenu_Right);
                }

                break;
        }
    }

    /**
     * 显示箭头（上/下）
     */
    private void showArrow() {
        View showArrow = null;
        switch (mDirection) {
            case DIRECTION_BOTTOM:
                setViewVisibility(mArrowUp, true);
                setViewVisibility(mArrowDown, false);
                showArrow = mArrowUp;
                break;
            case DIRECTION_TOP:
                setViewVisibility(mArrowDown, true);
                setViewVisibility(mArrowUp, false);
                showArrow = mArrowDown;
                break;
            case DIRECTION_NONE:
                setViewVisibility(mArrowDown, false);
                setViewVisibility(mArrowUp, false);
                break;
        }

        if (showArrow != null) {
            final int arrowWidth = mArrowUp.getMeasuredWidth();
            ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
            param.leftMargin = mArrowCenter - mX - arrowWidth / 2;
        }
    }

    @Override
    protected void setContentView(View root) {
        @SuppressLint("InflateParams") FrameLayout layout = (FrameLayout) LayoutInflater.from(mContext)
                .inflate(getRootLayout(), null, false);
        mArrowDown = layout.findViewById(R.id.arrow_down);
        mArrowUp = layout.findViewById(R.id.arrow_up);
        FrameLayout box = layout.findViewById(R.id.box);
        box.addView(root);
        super.setContentView(layout);
    }

    @Override
    protected void onWindowSizeChange() {

    }

    /**
     * the root layout: must provide ids: arrow_down(ImageView), arrow_up(ImageView), box(FrameLayout)
     */
    @LayoutRes
    private int getRootLayout() {
        return R.layout.pasc_popup_layout;
    }

    protected int getRootLayoutRadius(Context context) {
        return DensityUtil.dp2px(5);
    }

    private void setViewVisibility(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public ViewGroup.LayoutParams generateLayoutParam(int width, int height) {
        return new FrameLayout.LayoutParams(width, height);
    }

    @IntDef({DIRECTION_NONE, DIRECTION_TOP, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    @interface Direction {
    }

    @IntDef({ANIM_AUTO, ANIM_GROW_FROM_CENTER, ANIM_GROW_FROM_LEFT, ANIM_GROW_FROM_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface AnimStyle {
    }

    protected void build() {
        if (mLayoutResID != 0) {
            setContentView(mLayoutResID);
        }
        if (mRootView != null) {
            setContentView(mRootView);
        }
    }

    public static class PopupBuilder {
        private PascPopup mPopup;

        public PopupBuilder(Context context) {
            mPopup = createPopup(context);
        }

        protected PascPopup createPopup(Context context) {
            return new PascPopup(context);
        }

        /**
         * 菜单弹出动画
         *
         * @param animStyle 默认是 ANIM_AUTO
         *                  {@link AnimStyle#ANIM_AUTO,AnimStyle#ANIM_GROW_FROM_CENTER,AnimStyle#ANIM_GROW_FROM_LEFT,AnimStyle#ANIM_GROW_FROM_RIGHT}
         */
        public PopupBuilder setAnimStyle(@AnimStyle int animStyle) {
            mPopup.mAnimStyle = animStyle;
            return this;
        }

        /**
         * 设置Popup左（右）的最小Margin值
         *
         * @param popupLeftRightMinMargin 间距
         */
        public PopupBuilder setPopupLeftRightMinMargin(int popupLeftRightMinMargin) {
            mPopup.mPopupLeftRightMinMargin = popupLeftRightMinMargin;
            return this;
        }

        /**
         * 设置Popup上（下）的最小Margin值
         *
         * @param popupTopBottomMinMargin 间距
         */
        public PopupBuilder setPopupTopBottomMinMargin(int popupTopBottomMinMargin) {
            mPopup.mPopupTopBottomMinMargin = popupTopBottomMinMargin;
            return this;
        }

        /**
         * 设置位置的x偏移值
         *
         * @param offsetX 偏移量
         */
        public PopupBuilder setPositionOffsetX(int offsetX) {
            mPopup.mOffsetX = offsetX;
            return this;
        }

        /**
         * 设置位置的y偏移值（当在anchorView上方弹出）
         *
         * @param offsetYWhenTop mDirection!=DIRECTION_BOTTOM 时的 offsetY
         */
        public PopupBuilder setPositionOffsetYWhenTop(int offsetYWhenTop) {
            mPopup.mOffsetYWhenTop = offsetYWhenTop;
            return this;
        }

        /**
         * 设置位置的y偏移值（当在anchorView下方弹出）
         *
         * @param offsetYWhenBottom mDirection==DIRECTION_BOTTOM 时的 offsetY
         */
        public PopupBuilder setPositionOffsetYWhenBottom(int offsetYWhenBottom) {
            mPopup.mOffsetYWhenBottom = offsetYWhenBottom;
            return this;
        }

        /**
         * 设置Pop展示的位置
         *
         * @param preferredDirection {@link Direction#DIRECTION_BOTTOM}：底部，{@link Direction#DIRECTION_TOP}：顶部，{@link Direction#DIRECTION_NONE}：未定义
         */
        public PopupBuilder setPreferredDirection(@Direction int preferredDirection) {
            mPopup.mPreferredDirection = preferredDirection;
            return this;
        }

        /**
         * 设置 background drawable.
         *
         * @param background Background drawable
         */
        public PopupBuilder setBackgroundDrawable(Drawable background) {
            mPopup.mBackground = background;
            return this;
        }

        /**
         * 设置Popup content view.
         *
         * @param root Root view
         */
        public PopupBuilder setContentView(View root) {
            if (root == null) {
                throw new IllegalStateException("setContentView was not called with a view to display.");
            }
            mPopup.mRootView = root;
            return this;
        }

        /**
         * 设置content view.
         *
         * @param layoutResID Resource id
         */
        public PopupBuilder setContentView(int layoutResID) {
            if (layoutResID == 0) {
                throw new IllegalStateException("setContentView was not called with a view to display.");
            }
            mPopup.mLayoutResID = layoutResID;
            return this;
        }

        /**
         * 设置Popup隐藏时监听.
         *
         * @param listener 监听
         */
        public PopupBuilder setOnDismissListener(PopupWindow.OnDismissListener listener) {
            mPopup.mDismissListener = listener;
            return this;
        }

        /**
         * 设置ListPopup的宽度
         *
         * @param width 宽度
         */
        public PopupBuilder setListWidth(int width) {
            mPopup.mListWidth = width;
            return this;
        }

        /**
         * 设置ListPopup的最大高度
         *
         * @param maxHeight 宽度
         */
        public PopupBuilder setListMaxHeight(int maxHeight) {
            mPopup.mListMaxHeight = maxHeight;
            return this;
        }

        /**
         * 设置ListPopup的适配器
         *
         * @param adapter 适配器
         */
        public PopupBuilder setListAdapter(BaseAdapter adapter) {
            mPopup.mAdapter = adapter;
            return this;
        }

        public PascPopup create() {
            //构建PopWindow
            mPopup.build();
            return mPopup;
        }
    }

}
