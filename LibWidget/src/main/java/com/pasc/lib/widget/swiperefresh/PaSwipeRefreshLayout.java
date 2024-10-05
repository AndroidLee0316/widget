package com.pasc.lib.widget.swiperefresh;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ListViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pasc.lib.widget.R;

@SuppressLint("ClickableViewAccessibility")
public class PaSwipeRefreshLayout extends ViewGroup implements NestedScrollingParent,NestedScrollingChild {
    private static final String LOG_TAG = "PaSwipeRefreshLayout";
    private static final int HEADER_VIEW_HEIGHT = 50;// HeaderView height (dp)

    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
    private static final int INVALID_POINTER = -1;
    private static final float DRAG_RATE = .5f;

    private static final int SCALE_DOWN_DURATION = 150;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int DEFAULT_CIRCLE_TARGET = 64;

    private View mTarget;

    private OnPullRefreshListener mListener;// 下拉刷新listener
    private OnPushLoadMoreListener mOnPushLoadMoreListener;// 上拉加载更多

    private boolean mRefreshing = false;
    private boolean mLoadMore = false;
    private int mTouchSlop;
    private float mTotalDragDistance = -1;
    private int mMediumAnimationDuration;
    private int mCurrentTargetOffsetTop;
    private boolean mOriginalOffsetCalculated = false;

    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private int mActivePointerId = INVALID_POINTER;
    private boolean mScale;

    private boolean mReturningToStart;
    private final DecelerateInterpolator mDecelerateInterpolator;
    private static final int[] LAYOUT_ATTRS = new int[]{android.R.attr.enabled};

    private HeadViewContainer mHeadViewContainer;
    private RelativeLayout mFooterViewContainer;
    private int mHeaderViewIndex = -1;
    private int mFooterViewIndex = -1;

    protected int mFrom;

    private float mStartingScale;

    protected int mOriginalOffsetTop;

    private Animation mScaleAnimation;

    private Animation mScaleDownAnimation;

    private Animation mScaleDownToStartAnimation;

    private boolean mNestedScrollInProgress;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private OnChildScrollUpCallback mChildScrollUpCallback;
    private final int[] mParentScrollConsumed = new int[2];
    private final int[] mParentOffsetInWindow = new int[2];

    // 最后停顿时的偏移量px，与DEFAULT_CIRCLE_TARGET正比
    private float mSpinnerFinalOffset;

    private boolean mNotify;

    private int mHeaderViewWidth;// headerView的宽度

    private int mFooterViewWidth;

    private int mHeaderViewHeight;

    private int mFooterViewHeight;

    private boolean mUsingCustomStart;

    private boolean targetScrollWithLayout = true;

    private int pushDistance = 0;

    private View defaultHeadView = null;
    private TextView headTextView;
    private LinearLayout imgLin;

    private View tipsHeadView = null;
    private TextView tipsTextView;


    private View defaultFooterView = null;
    private TextView footerTextView;
    private LinearLayout imgFooterLin;

    private boolean isPullDown = true;



    private boolean isStartLoading = false;

    private boolean usingDefaultHeader = true;

    private float density = 1.0f;

    private boolean isProgressEnable = true;

    private ImageView mHeaderImg;
    private boolean isLoadMore = true;
    private boolean isPullRefresh = true;

    /**
     * 下拉时，超过距离之后，弹回来的动画监听器
     */
    private AnimationListener mRefreshListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            isProgressEnable = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isProgressEnable = true;
            if (mRefreshing) {
                if (mNotify) {
                    if (usingDefaultHeader) {
                    }
                    if (mListener != null) {
                        mListener.onRefresh();
                    }
                }
            } else {
                mHeadViewContainer.setVisibility(View.GONE);
                if (mScale) {
                    setAnimationProgress(0);
                } else {
                    setTargetOffsetTopAndBottom(mOriginalOffsetTop
                            - mCurrentTargetOffsetTop, true);
                }
            }
            mCurrentTargetOffsetTop = mHeadViewContainer.getTop();
        }
    };

    /**
     * 添加头布局
     *
     * @param child
     */
    public void setHeaderView(View child) {
        if (child == null) {
            return;
        }
        if (mHeadViewContainer == null) {
            return;
        }
        usingDefaultHeader = false;
        mHeadViewContainer.removeAllViews();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                mHeaderViewWidth, mHeaderViewHeight);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mHeadViewContainer.addView(child, layoutParams);
    }

    public void setFooterView(View child) {
        if (child == null) {
            return;
        }
        if (mFooterViewContainer == null) {
            return;
        }
        mFooterViewContainer.removeAllViews();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                mFooterViewWidth, mFooterViewHeight);
        mFooterViewContainer.addView(child, layoutParams);
    }

    public PaSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    @SuppressWarnings("deprecation")
    public PaSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * getScaledTouchSlop是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件。如果小于这个距离就不触发移动控件
         */
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mMediumAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);

        setWillNotDraw(false);
        mDecelerateInterpolator = new DecelerateInterpolator(
                DECELERATE_INTERPOLATION_FACTOR);

        final TypedArray a = context
                .obtainStyledAttributes(attrs, LAYOUT_ATTRS);
        setEnabled(a.getBoolean(0, true));
        a.recycle();

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mHeaderViewWidth = (int) display.getWidth();
        mFooterViewWidth = (int) display.getWidth();
        mHeaderViewHeight = (int) (HEADER_VIEW_HEIGHT * metrics.density);
        mFooterViewHeight = (int) (HEADER_VIEW_HEIGHT * metrics.density);
//        defaultProgressView = new CircleProgressView(getContext());
        defaultHeadView = LayoutInflater.from(getContext())
                .inflate(R.layout.swipe_head_view, null);
        headTextView =  defaultHeadView.findViewById(R.id.text_view);
        imgLin =  defaultHeadView.findViewById(R.id.img_lin);
        mHeaderImg = defaultHeadView.findViewById(R.id.img);
        headTextView.setText(getResources().getText(R.string.pull_refresh_text));

        defaultFooterView = LayoutInflater.from(getContext())
                .inflate(R.layout.swipe_footer_view, null);
        footerTextView =  defaultFooterView.findViewById(R.id.text_view);
        imgFooterLin =  defaultFooterView.findViewById(R.id.img_lin);
        footerTextView.setText(getResources().getText(R.string.load_text));

        tipsHeadView = LayoutInflater.from(getContext())
                .inflate(R.layout.swipe_head_tips, null);
        tipsTextView = tipsHeadView.findViewById(R.id.title_tv);

        createHeaderViewContainer();
        createFooterViewContainer();
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setChildrenDrawingOrderEnabled(true);
        setNestedScrollingEnabled(true);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        mSpinnerFinalOffset = DEFAULT_CIRCLE_TARGET * metrics.density;
        density = metrics.density;
        mTotalDragDistance = mSpinnerFinalOffset;
    }


    public void startLoading() {
        isStartLoading = true;
        headTextView.setVisibility(GONE);
        imgLin.setVisibility(VISIBLE);
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        imgLin.startAnimation(animation);
    }

    public void onStopLoading(){
        isStartLoading = false;
        headTextView.setVisibility(VISIBLE);
        imgLin.setVisibility(GONE);
        imgLin.clearAnimation();
    }

    public void setHeaderImg(int res){
        mHeaderImg.setImageResource(res);
    }
    public void setPushLoadMoreEnable(boolean enable){
        isLoadMore = enable;
    }
    public void setPullRefreshEnable(boolean enable){
        isPullRefresh = enable;
    }


    public void startFooterLoading() {
        headTextView.setVisibility(GONE);
        imgFooterLin.setVisibility(VISIBLE);
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        imgFooterLin.startAnimation(animation);
    }

    public void onStopFooterLoading(){
        headTextView.setVisibility(VISIBLE);
        imgFooterLin.setVisibility(GONE);
        imgFooterLin.clearAnimation();
    }

    /**
     * 孩子节点绘制的顺序
     *
     * @param childCount
     * @param i
     * @return
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        // 将新添加的View,放到最后绘制
        if (mHeaderViewIndex < 0 && mFooterViewIndex < 0) {
            return i;
        }
        if (i == childCount - 2) {
            return mHeaderViewIndex;
        }
        if (i == childCount - 1) {
            return mFooterViewIndex;
        }
        int bigIndex = mFooterViewIndex > mHeaderViewIndex ? mFooterViewIndex
                : mHeaderViewIndex;
        int smallIndex = mFooterViewIndex < mHeaderViewIndex ? mFooterViewIndex
                : mHeaderViewIndex;
        if (i >= smallIndex && i < bigIndex - 1) {
            return i + 1;
        }
        if (i >= bigIndex || (i == bigIndex - 1)) {
            return i + 2;
        }
        return i;
    }

    /**
     * 创建头布局的容器
     */
    private void createHeaderViewContainer() {
        if(mHeadViewContainer == null ){
            mHeadViewContainer = new HeadViewContainer(getContext());
        }
        mHeadViewContainer.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                mHeaderViewWidth, mHeaderViewHeight);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mHeadViewContainer.addView(defaultHeadView, layoutParams);
        addView(mHeadViewContainer);
    }

    /**
     * 添加底部布局
     */
    private void createFooterViewContainer() {
        mFooterViewContainer = new RelativeLayout(getContext());
        mFooterViewContainer.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                mFooterViewWidth,  mFooterViewHeight);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mFooterViewContainer.addView(defaultFooterView, layoutParams);
        addView(mFooterViewContainer);
    }

    /**
     * 设置
     *
     * @param listener
     */
    public void setOnPullRefreshListener(OnPullRefreshListener listener) {
        mListener = listener;
    }

    public void setHeaderViewBackgroundColor(int color) {
        mHeadViewContainer.setBackgroundColor(color);
    }

    /**
     * 设置上拉加载更多的接口
     *
     * @param onPushLoadMoreListener
     */
    public void setOnPushLoadMoreListener(
            OnPushLoadMoreListener onPushLoadMoreListener) {
        this.mOnPushLoadMoreListener = onPushLoadMoreListener;
    }

    /**
     * Notify the widget that refresh state has changed. Do not call this when
     * refresh is triggered by a swipe gesture.
     *
     * @param refreshing Whether or not the view should show refresh progress.
     */
    public void setRefreshing(boolean refreshing) {

        if (refreshing && mRefreshing != refreshing) {
            // scale and show
            mRefreshing = refreshing;
            int endTarget = 0;
            if (!mUsingCustomStart) {
                endTarget = (int) (mSpinnerFinalOffset + mOriginalOffsetTop);
            } else {
                endTarget = (int) mSpinnerFinalOffset;
            }
            setTargetOffsetTopAndBottom(endTarget - mCurrentTargetOffsetTop,
                    true /* requires update */);
            mNotify = false;
            startScaleUpAnimation(mRefreshListener);
        } else {
            setRefreshing(refreshing, false /* notify */);
        }
    }
    public void setTips(final View child){

        if (child == null) {
            return;
        }
        if (mHeadViewContainer == null) {
            return;
        }
        usingDefaultHeader = false;
        mHeadViewContainer.removeAllViews();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                mHeaderViewWidth, mHeaderViewHeight);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.bottomMargin = 45;
        mHeadViewContainer.addView(child,layoutParams);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshing(false);

                usingDefaultHeader = true;
                mHeadViewContainer.removeAllViews();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        mHeaderViewWidth, mHeaderViewHeight);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                mHeadViewContainer.addView(defaultHeadView, layoutParams);
            }
        }, 1000);

    }

    private void startScaleUpAnimation(AnimationListener listener) {
        mHeadViewContainer.setVisibility(View.VISIBLE);
        mScaleAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime,
                                            Transformation t) {
                setAnimationProgress(interpolatedTime);
            }
        };
        mScaleAnimation.setDuration(mMediumAnimationDuration);
        if (listener != null) {
            mHeadViewContainer.setAnimationListener(listener);
        }
        mHeadViewContainer.clearAnimation();
        mHeadViewContainer.startAnimation(mScaleAnimation);
    }

    private void setAnimationProgress(float progress) {
        if (!usingDefaultHeader) {
            progress = 1;
        }
        ViewCompat.setScaleX(mHeadViewContainer, progress);
        ViewCompat.setScaleY(mHeadViewContainer, progress);
    }

    private void setRefreshing(boolean refreshing, final boolean notify) {
        if (mRefreshing != refreshing) {
            mNotify = notify;
            ensureTarget();
            mRefreshing = refreshing;
            if (mRefreshing) {
                animateOffsetToCorrectPosition(mCurrentTargetOffsetTop,
                        mRefreshListener);
            } else {
                //startScaleDownAnimation(mRefreshListener);
                animateOffsetToStartPosition(mCurrentTargetOffsetTop, mRefreshListener);
            }
        }
    }

    private void startScaleDownAnimation(AnimationListener listener) {
        mScaleDownAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime,
                                            Transformation t) {
                setAnimationProgress(1 - interpolatedTime);
            }
        };
        mScaleDownAnimation.setDuration(SCALE_DOWN_DURATION);
        mHeadViewContainer.setAnimationListener(listener);
        mHeadViewContainer.clearAnimation();
        mHeadViewContainer.startAnimation(mScaleDownAnimation);
    }

    public boolean isRefreshing() {
        return mRefreshing;
    }

    /**
     * 确保mTarget不为空<br>
     * mTarget一般是可滑动的ScrollView,ListView,RecyclerView等
     */
    private void ensureTarget() {
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(mHeadViewContainer)
                        && !child.equals(mFooterViewContainer)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    /**
     * Set the distance to trigger a sync in dips
     *
     * @param distance
     */
    public void setDistanceToTriggerSync(int distance) {
        mTotalDragDistance = distance;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        int distance = mCurrentTargetOffsetTop + mHeadViewContainer.getMeasuredHeight();
        if (!targetScrollWithLayout) {
            // 判断标志位，如果目标View不跟随手指的滑动而滑动，将下拉偏移量设置为0
            distance = 0;
        }
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop() + distance - pushDistance;// 根据偏移量distance更新
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        Log.d(LOG_TAG, "debug:onLayout childHeight = " + childHeight);
        child.layout(childLeft, childTop, childLeft + childWidth, childTop
                + childHeight);// 更新目标View的位置
        int headViewWidth = mHeadViewContainer.getMeasuredWidth();
        int headViewHeight = mHeadViewContainer.getMeasuredHeight();
        mHeadViewContainer.layout((width / 2 - headViewWidth / 2),
                mCurrentTargetOffsetTop, (width / 2 + headViewWidth / 2),
                mCurrentTargetOffsetTop + headViewHeight);// 更新头布局的位置
        int footViewWidth = mFooterViewContainer.getMeasuredWidth();
        int footViewHeight = mFooterViewContainer.getMeasuredHeight();
        mFooterViewContainer.layout((width / 2 - footViewWidth / 2), height
                - pushDistance, (width / 2 + footViewWidth / 2), height
                + footViewHeight - pushDistance);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        mTarget.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth()
                        - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight()
                                - getPaddingTop() - getPaddingBottom(),
                        MeasureSpec.EXACTLY));
        mHeadViewContainer.measure(MeasureSpec.makeMeasureSpec(
                mHeaderViewWidth, MeasureSpec.EXACTLY), MeasureSpec
                .makeMeasureSpec(3 * mHeaderViewHeight, MeasureSpec.EXACTLY));
        mFooterViewContainer.measure(MeasureSpec.makeMeasureSpec(
                mFooterViewWidth, MeasureSpec.EXACTLY), MeasureSpec
                .makeMeasureSpec(mFooterViewHeight, MeasureSpec.EXACTLY));
        if (!mUsingCustomStart && !mOriginalOffsetCalculated) {
            mOriginalOffsetCalculated = true;
            mCurrentTargetOffsetTop = mOriginalOffsetTop = -mHeadViewContainer
                    .getMeasuredHeight();
        }
        mHeaderViewIndex = -1;
        for (int index = 0; index < getChildCount(); index++) {
            if (getChildAt(index) == mHeadViewContainer) {
                mHeaderViewIndex = index;
                break;
            }
        }
        mFooterViewIndex = -1;
        for (int index = 0; index < getChildCount(); index++) {
            if (getChildAt(index) == mFooterViewContainer) {
                mFooterViewIndex = index;
                break;
            }
        }
    }

    /**
     * 判断目标View是否滑动到顶部-还能否继续滑动
     *
     * @return
     */
    public boolean isChildScrollToTop() {
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return !(absListView.getChildCount() > 0 && (absListView
                        .getFirstVisiblePosition() > 0 || absListView
                        .getChildAt(0).getTop() < absListView.getPaddingTop()));
            } else {
                return !(mTarget.getScrollY() > 0);
            }
        } else {
            return !ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 是否滑动到底部
     *
     * @return
     */
    public boolean isChildScrollToBottom() {
        if (isChildScrollToTop()) {
            return false;
        }
        if (mTarget instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) mTarget;
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            int count = recyclerView.getAdapter().getItemCount();
            if (layoutManager instanceof LinearLayoutManager && count > 0) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == count - 1) {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] lastItems = new int[2];
                staggeredGridLayoutManager
                        .findLastCompletelyVisibleItemPositions(lastItems);
                int lastItem = Math.max(lastItems[0], lastItems[1]);
                if (lastItem == count - 1) {
                    return true;
                }
            }
            return false;
        } else if (mTarget instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) mTarget;
            int count = absListView.getAdapter().getCount();
            int fristPos = absListView.getFirstVisiblePosition();
            if (fristPos == 0
                    && absListView.getChildAt(0).getTop() >= absListView
                    .getPaddingTop()) {
                return false;
            }
            int lastPos = absListView.getLastVisiblePosition();
            if (lastPos > 0 && count > 0 && lastPos == count - 1) {
                return true;
            }
            return false;
        } else if (mTarget instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) mTarget;
            View view = (View) scrollView
                    .getChildAt(scrollView.getChildCount() - 1);
            if (view != null) {
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView
                        .getScrollY()));
                if (diff == 0) {
                    return true;
                }
            }
        } else if (mTarget instanceof NestedScrollView) {
            NestedScrollView nestedScrollView = (NestedScrollView) mTarget;
            View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
            if (view != null) {
                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
                if (diff == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 主要判断是否应该拦截子View的事件<br>
     * 如果拦截，则交给自己的OnTouchEvent处理<br>
     * 否者，交给子View处理<br>
     */
    @SuppressLint("LongLogTag")
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        final int action = MotionEventCompat.getActionMasked(ev);

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }
        if (!isEnabled() || mReturningToStart || mRefreshing
                || mLoadMore || mNestedScrollInProgress
                || (!isChildScrollToTop() && !isChildScrollToBottom())) {
            // 如果子View可以滑动，不拦截事件，交给子View处理-下拉刷新
            // 或者子View没有滑动到底部不拦截事件-上拉加载更多
            return false;
        }

        // 下拉刷新判断
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setTargetOffsetTopAndBottom(
                        mOriginalOffsetTop - mHeadViewContainer.getTop(), true);// 恢复HeaderView的初始位置
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                final float initialMotionY = getMotionEventY(ev, mActivePointerId);
                if (initialMotionY == -1) {
                    return false;
                }
                mInitialMotionY = initialMotionY;// 记录按下的位置

            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    Log.e(LOG_TAG,
                            "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }

                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                float yDiff = 0;
                if (!isChildScrollToBottom()) {

                    yDiff = Math.abs(y - mInitialMotionY);
                    if (yDiff > mTouchSlop && !mIsBeingDragged) {// 判断滑动的距离足够
                        if((y - mInitialMotionY) > 0){
                            isPullDown = true;
                        }else {
                            isPullDown = false;
                        }
                        mIsBeingDragged = true;
                    }
                }
                break;

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }

        return mIsBeingDragged;// 如果正在拖动，则拦截子View的事件
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev,
                activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (usingDefaultHeader) {
            if(isStartLoading){
                onStopLoading();
            }
        }


        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }
        if (!isEnabled() || mReturningToStart || mNestedScrollInProgress|| canChildScrollUp()
                || (!isChildScrollToTop() && !isChildScrollToBottom())) {
            // 如果子View可以滑动，不拦截事件，交给子View处理
            return false;
        }

        if(isPullDown){
            if(isPullRefresh){
                return handlerPullTouchEvent(ev, action);
            }

        }else {
            if(isLoadMore){
                return handlerPushTouchEvent(ev, action);
            }

        }

        return false;
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback callback) {
        mChildScrollUpCallback = callback;
    }

    public boolean canChildScrollUp() {
        if (mChildScrollUpCallback != null) {
            return mChildScrollUpCallback.canChildScrollUp(this, mTarget);
        }
        if (mTarget instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mTarget, -1);
        }
        return mTarget.canScrollVertically(-1);
    }
    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        if ((android.os.Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
            // Nope.
        } else {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }


    // NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(
                dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX,
                                    float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY,
                                 boolean consumed) {
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }




    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        // Now let our nested parent consume the leftovers
        final int[] parentConsumed = mParentScrollConsumed;
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] += parentConsumed[0];
            consumed[1] += parentConsumed[1];
        }
    }
    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return isEnabled() && !mReturningToStart && !mRefreshing
                && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        // Reset the counter of how much leftover scroll needs to be consumed.
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        // Dispatch up to the nested parent
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
        mNestedScrollInProgress = true;
    }
    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
        mNestedScrollInProgress = false;
        stopNestedScroll();
    }

    @Override
    public void onNestedScroll(final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        // Dispatch up to the nested parent first
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                mParentOffsetInWindow);
        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
        if (dy < 0 && !canChildScrollUp()) {
            mNestedScrollInProgress = false;
        }else if(dy > 0 && !canChildScrollUp()){
            mNestedScrollInProgress = false;
        }
    }


    @SuppressLint("LongLogTag")
    private boolean handlerPullTouchEvent(MotionEvent ev, int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                headTextView.setVisibility(VISIBLE);
                imgLin.setVisibility(GONE);
                break;

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG,
                            "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
//                if (mIsBeingDragged) {
                    float originalDragPercent = overscrollTop / mTotalDragDistance;
                    if (originalDragPercent < 0) {
                        return false;
                    }
                    float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
                    float extraOS = Math.abs(overscrollTop) - mTotalDragDistance;
                    float slingshotDist = mUsingCustomStart ? mSpinnerFinalOffset
                            - mOriginalOffsetTop : mSpinnerFinalOffset;
                    float tensionSlingshotPercent = Math.max(0,
                            Math.min(extraOS, slingshotDist * 2) / slingshotDist);
                    float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math
                            .pow((tensionSlingshotPercent / 4), 2)) * 2f;
                    float extraMove = (slingshotDist) * tensionPercent * 2;

                    int targetY = mOriginalOffsetTop
                            + (int) ((slingshotDist * dragPercent) + extraMove);
                    if (mHeadViewContainer.getVisibility() != View.VISIBLE) {
                        mHeadViewContainer.setVisibility(View.VISIBLE);
                    }
                    if (!mScale) {
                        ViewCompat.setScaleX(mHeadViewContainer, 1f);
                        ViewCompat.setScaleY(mHeadViewContainer, 1f);
                    }
                    if (usingDefaultHeader) {
                        float alpha = overscrollTop / mTotalDragDistance;
                        if (alpha >= 1.0f) {
                            alpha = 1.0f;
                        }
                    }
                    if (overscrollTop < mTotalDragDistance) {

                        if (usingDefaultHeader) {
                            headTextView.setText(getResources().getText(R.string.pull_refresh_text));
                            headTextView.setVisibility(VISIBLE);
                            imgLin.setVisibility(GONE);
                        }

                        if (mScale) {
                            setAnimationProgress(overscrollTop / mTotalDragDistance);
                        }
                        if (mListener != null) {
                            mListener.onPullEnable(false);
                        }
                    } else {
                        if (usingDefaultHeader) {
                            headTextView.setText(getResources().getText(R.string.let_go_refresh_text));
                            headTextView.setVisibility(VISIBLE);
                            imgLin.setVisibility(GONE);
                        }

                        if (mListener != null) {
                            mListener.onPullEnable(true);
                        }

                    }
                    setTargetOffsetTopAndBottom(targetY - mCurrentTargetOffsetTop,
                            true);
//                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
                if (usingDefaultHeader) {
                    startLoading();
                }


            case MotionEvent.ACTION_CANCEL: {

                if (mActivePointerId == INVALID_POINTER) {
                    if (action == MotionEvent.ACTION_UP) {
                        Log.e(LOG_TAG,
                                "Got ACTION_UP event but don't have an active pointer id.");
                    }
                    return false;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                mIsBeingDragged = false;
                if (overscrollTop > mTotalDragDistance) {
                    setRefreshing(true, true /* notify */);
                } else {
                    mRefreshing = false;
                    AnimationListener listener = null;
                    if (!mScale) {
                        listener = new AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (!mScale) {
                                    startScaleDownAnimation(null);
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                        };
                    }
                    animateOffsetToStartPosition(mCurrentTargetOffsetTop, listener);
                }
                mActivePointerId = INVALID_POINTER;
                return false;
            }
        }

        return true;
    }

    /**
     * 处理上拉加载更多的Touch事件
     *
     * @param ev
     * @param action
     * @return
     */
    @SuppressLint("LongLogTag")
    private boolean handlerPushTouchEvent(MotionEvent ev, int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                Log.d(LOG_TAG, "debug:onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG,
                            "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollBottom = (mInitialMotionY - y) * DRAG_RATE;
//                if (mIsBeingDragged) {
                    pushDistance = (int) overscrollBottom;
                    updateFooterViewPosition();
                    if (mOnPushLoadMoreListener != null) {
                        startFooterLoading();
                        mOnPushLoadMoreListener
                                .onPushEnable(pushDistance >= mFooterViewHeight);
                    }
//                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (mActivePointerId == INVALID_POINTER) {
                    if (action == MotionEvent.ACTION_UP) {
                        Log.e(LOG_TAG,
                                "Got ACTION_UP event but don't have an active pointer id.");
                    }
                    return false;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollBottom = (mInitialMotionY - y) * DRAG_RATE;// 松手是下拉的距离
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                if (overscrollBottom < mFooterViewHeight
                        || mOnPushLoadMoreListener == null) {// 直接取消
                    pushDistance = 0;
                } else {// 下拉到mFooterViewHeight
                    pushDistance = mFooterViewHeight;
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    updateFooterViewPosition();
                    if (pushDistance == mFooterViewHeight
                            && mOnPushLoadMoreListener != null) {
                        mLoadMore = true;
                        mOnPushLoadMoreListener.onLoadMore();
                    }
                } else {
                    animatorFooterToBottom((int) overscrollBottom, pushDistance);
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 松手之后，使用动画将Footer从距离start变化到end
     *
     * @param start
     * @param end
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animatorFooterToBottom(int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(150);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // update
                pushDistance = (Integer) valueAnimator.getAnimatedValue();
                updateFooterViewPosition();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (end > 0 && mOnPushLoadMoreListener != null) {
                    // start loading more
                    mLoadMore = true;
                    mOnPushLoadMoreListener.onLoadMore();
                } else {
                    resetTargetLayout();
                    mLoadMore = false;
                }
            }
        });
        valueAnimator.setInterpolator(mDecelerateInterpolator);
        valueAnimator.start();
    }



    public void setFooterNotMoreContent(){
        footerTextView.setText("无更多内容");
    }
    /**
     * 设置停止加载
     *
     * @param loadMore
     */
    public void setLoadMore(boolean loadMore) {
        if (!loadMore && mLoadMore) {// 停止加载
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                mLoadMore = false;
                pushDistance = 0;
                updateFooterViewPosition();
            } else {
                animatorFooterToBottom(mFooterViewHeight, 0);
            }
        }
    }

    private void animateOffsetToCorrectPosition(int from,
                                                AnimationListener listener) {
        mFrom = from;
        mAnimateToCorrectPosition.reset();
        mAnimateToCorrectPosition.setDuration(ANIMATE_TO_TRIGGER_DURATION);
        mAnimateToCorrectPosition.setInterpolator(mDecelerateInterpolator);
        if (listener != null) {
            mHeadViewContainer.setAnimationListener(listener);
        }
        mHeadViewContainer.clearAnimation();
        mHeadViewContainer.startAnimation(mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int from,
                                              AnimationListener listener) {
        if (mScale) {
            startScaleDownReturnToStartAnimation(from, listener);
        } else {
            mFrom = from;
            mAnimateToStartPosition.reset();
            mAnimateToStartPosition.setDuration(ANIMATE_TO_START_DURATION);
            mAnimateToStartPosition.setInterpolator(mDecelerateInterpolator);
            if (listener != null) {
                mHeadViewContainer.setAnimationListener(listener);
            }
            mHeadViewContainer.clearAnimation();
            mHeadViewContainer.startAnimation(mAnimateToStartPosition);
        }
        resetTargetLayoutDelay(ANIMATE_TO_START_DURATION);
    }

    /**
     * 重置Target位置
     *
     * @param delay
     */
    public void resetTargetLayoutDelay(int delay) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                resetTargetLayout();
            }
        }, delay);
    }

    /**
     * 重置Target的位置
     */
    public void resetTargetLayout() {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = child.getWidth() - getPaddingLeft()
                - getPaddingRight();
        final int childHeight = child.getHeight() - getPaddingTop()
                - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop
                + childHeight);

        int headViewWidth = mHeadViewContainer.getMeasuredWidth();
        int headViewHeight = mHeadViewContainer.getMeasuredHeight();
        mHeadViewContainer.layout((width / 2 - headViewWidth / 2),
                -headViewHeight, (width / 2 + headViewWidth / 2), 0);// 更新头布局的位置
        int footViewWidth = mFooterViewContainer.getMeasuredWidth();
        int footViewHeight = mFooterViewContainer.getMeasuredHeight();
        mFooterViewContainer.layout((width / 2 - footViewWidth / 2), height,
                (width / 2 + footViewWidth / 2), height + footViewHeight);
    }

    private final Animation mAnimateToCorrectPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int targetTop = 0;
            int endTarget = 0;
            if (!mUsingCustomStart) {
                endTarget = (int) (mSpinnerFinalOffset - Math
                        .abs(mOriginalOffsetTop));
            } else {
                endTarget = (int) mSpinnerFinalOffset;
            }
            targetTop = (mFrom + (int) ((endTarget - mFrom) * interpolatedTime));
            int offset = targetTop - mHeadViewContainer.getTop();
            setTargetOffsetTopAndBottom(offset, false /* requires update */);
        }

        @Override
        public void setAnimationListener(AnimationListener listener) {
            super.setAnimationListener(listener);
        }
    };

    private void moveToStart(float interpolatedTime) {
        int targetTop = 0;
        targetTop = (mFrom + (int) ((mOriginalOffsetTop - mFrom) * interpolatedTime));
        int offset = targetTop - mHeadViewContainer.getTop();
        setTargetOffsetTopAndBottom(offset, false /* requires update */);
    }

    private final Animation mAnimateToStartPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            moveToStart(interpolatedTime);
        }
    };

    private void startScaleDownReturnToStartAnimation(int from,
                                                      AnimationListener listener) {
        mFrom = from;
        mStartingScale = ViewCompat.getScaleX(mHeadViewContainer);
        mScaleDownToStartAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime,
                                            Transformation t) {
                float targetScale = (mStartingScale + (-mStartingScale * interpolatedTime));
                setAnimationProgress(targetScale);
                moveToStart(interpolatedTime);
            }
        };
        mScaleDownToStartAnimation.setDuration(SCALE_DOWN_DURATION);
        if (listener != null) {
            mHeadViewContainer.setAnimationListener(listener);
        }
        mHeadViewContainer.clearAnimation();
        mHeadViewContainer.startAnimation(mScaleDownToStartAnimation);
    }

    private void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
        mHeadViewContainer.bringToFront();
        mHeadViewContainer.offsetTopAndBottom(offset);
        mCurrentTargetOffsetTop = mHeadViewContainer.getTop();
        if (requiresUpdate && Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    /**
     * 修改底部布局的位置-敏感pushDistance
     */
    private void updateFooterViewPosition() {
        mFooterViewContainer.setVisibility(View.VISIBLE);
        mFooterViewContainer.bringToFront();
        //针对4.4及之前版本的兼容
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mFooterViewContainer.getParent().requestLayout();
        }
        mFooterViewContainer.offsetTopAndBottom(-pushDistance);
    }


    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev,
                    newPointerIndex);
        }
    }

    /**
     * @Description 下拉刷新布局头部的容器
     */
    private class HeadViewContainer extends RelativeLayout {

        private AnimationListener mListener;

        public HeadViewContainer(Context context) {
            super(context);
        }

        public void setAnimationListener(AnimationListener listener) {
            mListener = listener;
        }

        @Override
        public void onAnimationStart() {
            super.onAnimationStart();
            if (mListener != null) {
                mListener.onAnimationStart(getAnimation());
            }
        }

        @Override
        public void onAnimationEnd() {
            super.onAnimationEnd();
            if (mListener != null) {
                mListener.onAnimationEnd(getAnimation());
            }
        }
    }

    /**
     * 判断子View是否跟随手指的滑动而滑动，默认跟随
     *
     * @return
     */
    public boolean isTargetScrollWithLayout() {
        return targetScrollWithLayout;
    }

    /**
     * 设置子View是否跟谁手指的滑动而滑动
     *
     * @param targetScrollWithLayout
     */
    public void setTargetScrollWithLayout(boolean targetScrollWithLayout) {
        this.targetScrollWithLayout = targetScrollWithLayout;
    }

    /**
     * 下拉刷新回调
     */
    public interface OnPullRefreshListener {
        public void onRefresh();

        public void onPullEnable(boolean enable);
    }

    /**
     * 上拉加载更多
     */
    public interface OnPushLoadMoreListener {
        public void onLoadMore();

        public void onPushEnable(boolean enable);
    }

    /**
     * Adapter
     */
    public class OnPullRefreshListenerAdapter implements OnPullRefreshListener {

        @Override
        public void onRefresh() {

        }

        @Override
        public void onPullEnable(boolean enable) {

        }

    }

    public class OnPushLoadMoreListenerAdapter implements
            OnPushLoadMoreListener {

        @Override
        public void onLoadMore() {

        }

        @Override
        public void onPushEnable(boolean enable) {

        }

    }

    public interface OnChildScrollUpCallback {
        /**
         * Callback that will be called when {@link SwipeRefreshLayout#canChildScrollUp()} method
         * is called to allow the implementer to override its behavior.
         *
         * @param parent SwipeRefreshLayout that this callback is overriding.
         * @param child The child view of SwipeRefreshLayout.
         *
         * @return Whether it is possible for the child view of parent layout to scroll up.
         */
        boolean canChildScrollUp(@NonNull PaSwipeRefreshLayout parent, @Nullable View child);
    }

}
