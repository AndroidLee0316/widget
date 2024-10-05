package com.pasc.lib.widget.banner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.banner.animations.BaseAnimationInterface;
import com.pasc.lib.widget.banner.animations.DescriptionAnimation;
import com.pasc.lib.widget.banner.imageloader.ScaleType;
import com.pasc.lib.widget.banner.imageloader.impl.GlideImpl;
import com.pasc.lib.widget.banner.indicators.PagerIndicator;
import com.pasc.lib.widget.banner.slidertypes.BaseSliderView;
import com.pasc.lib.widget.banner.slidertypes.DefaultSliderView;
import com.pasc.lib.widget.banner.transformers.AccordionTransformer;
import com.pasc.lib.widget.banner.transformers.BackgroundToForegroundTransformer;
import com.pasc.lib.widget.banner.transformers.BaseTransformer;
import com.pasc.lib.widget.banner.transformers.CubeInTransformer;
import com.pasc.lib.widget.banner.transformers.DefaultTransformer;
import com.pasc.lib.widget.banner.transformers.DepthPageTransformer;
import com.pasc.lib.widget.banner.transformers.FadeTransformer;
import com.pasc.lib.widget.banner.transformers.FlipHorizontalTransformer;
import com.pasc.lib.widget.banner.transformers.FlipPageViewTransformer;
import com.pasc.lib.widget.banner.transformers.ForegroundToBackgroundTransformer;
import com.pasc.lib.widget.banner.transformers.RotateDownTransformer;
import com.pasc.lib.widget.banner.transformers.RotateUpTransformer;
import com.pasc.lib.widget.banner.transformers.StackTransformer;
import com.pasc.lib.widget.banner.transformers.TabletTransformer;
import com.pasc.lib.widget.banner.transformers.ZoomInTransformer;
import com.pasc.lib.widget.banner.transformers.ZoomOutSlideTransformer;
import com.pasc.lib.widget.banner.transformers.ZoomOutTransformer;
import com.pasc.lib.widget.banner.tricks.FixedSpeedScroller;
import com.pasc.lib.widget.banner.tricks.InfinitePagerAdapter;
import com.pasc.lib.widget.banner.tricks.InfiniteViewPager;
import com.pasc.lib.widget.banner.tricks.ViewPagerEx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * SliderLayout is compound layout. This is combined with {@link PagerIndicator}
 * and {@link ViewPagerEx} .
 * <p>
 * There is some properties you can set in XML:
 * <p>
 * indicator_visibility
 * visible
 * invisible
 * <p>
 * indicator_shape
 * oval
 * rect
 * <p>
 * indicator_selected_color
 * <p>
 * indicator_unselected_color
 * <p>
 * indicator_selected_drawable
 * <p>
 * indicator_unselected_drawable
 * <p>
 * pager_animation
 * Default
 * Accordion
 * Background2Foreground
 * CubeIn
 * DepthPage
 * Fade
 * FlipHorizontal
 * FlipPage
 * Foreground2Background
 * RotateDown
 * RotateUp
 * Stack
 * Tablet
 * ZoomIn
 * ZoomOutSlide
 * ZoomOut
 * <p>
 * pager_animation_span
 */
public class SliderLayout extends RelativeLayout {

    private static final String TAG = "SliderLayout";
    private Context mContext;
    /**
     * InfiniteViewPager is extended from ViewPagerEx. As the name says, it can scroll without bounder.
     */
    private InfiniteViewPager mViewPager;

    /**
     * InfiniteViewPager ListAdapter.
     */
    private SliderAdapter mSliderAdapter;

    /**
     * {@link ViewPagerEx} indicator.
     */
    private PagerIndicator mIndicator;

    /**
     * Determine if auto recover after user touch the {@link ViewPagerEx}
     */
    private boolean mAutoRecover = true;

    private int pagerAnimationIndex;

    /**
     * {@link ViewPagerEx} transformer time span.
     */
    private int pagerAnimationSpan = 1000;

    private boolean mAutoCycle; // 是否自动滚动

    /**
     * the duration between animation.
     */
    private long pagerAnimationDuration = 4000;

    private int imgRadius;

    /**
     * Visibility of {@link PagerIndicator}
     */
    private PagerIndicator.IndicatorVisibility mIndicatorVisibility = PagerIndicator.IndicatorVisibility.Visible;

    /**
     * {@link ViewPagerEx} 's transformer
     */
    private BaseTransformer mViewPagerTransformer;

    /**
     * @see BaseAnimationInterface
     */
    private BaseAnimationInterface mCustomAnimation;

    private boolean enableHandSlide = true; // 是否允许手动滑动，默认是允许的

    private ScreenBroadcastReceiver mScreenBroadcastReceiver;

    private IntentFilter filter = new IntentFilter();

    int defaultImgRes = R.drawable.bg_default_img; // 默认的图片资源

    public int getDefaultImg() {
        return defaultImgRes;
    }

    public void setDefaultImg(int defaultImgRes) {
        this.defaultImgRes = defaultImgRes;
    }
    @Deprecated
    public int getDefaultImgRes() {
        return defaultImgRes;
    }
    @Deprecated
    public void setDefaultImgRes(int defaultImgRes) {
        this.defaultImgRes = defaultImgRes;
    }


    public SliderLayout(Context context) {
        this(context, null);
    }

    public SliderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SliderStyle);
    }

    public SliderLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.slider_layout, this, true);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SliderLayout,
                defStyle, 0);

        pagerAnimationSpan = attributes.getInteger(R.styleable.SliderLayout_pager_animation_span, 1000);
        pagerAnimationDuration = attributes.getInteger(R.styleable.SliderLayout_pager_animation_duration, 4000);
        pagerAnimationIndex = attributes.getInt(R.styleable.SliderLayout_pager_animation, PagerAnimation.Default.ordinal());
        mAutoCycle = attributes.getBoolean(R.styleable.SliderLayout_auto_cycle, true);
        ratio = attributes.getFloat(R.styleable.SliderLayout_ratio, 2);
        defaultImgRes = attributes.getResourceId(R.styleable.SliderLayout_default_img, R.drawable.bg_default_img);
        imgRadius = (int) attributes.getDimension(R.styleable.SliderLayout_img_radius,
                0);
        int visibility = attributes.getInt(R.styleable.SliderLayout_indicator_visibility, 0);
        for (PagerIndicator.IndicatorVisibility v : PagerIndicator.IndicatorVisibility.values()) {
            if (v.ordinal() == visibility) {
                mIndicatorVisibility = v;
                break;
            }
        }
        mSliderAdapter = new SliderAdapter(mContext);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSliderAdapter);

        mViewPager = (InfiniteViewPager) findViewById(R.id.daimajia_slider_viewpager);
        mViewPager.setAdapter(wrappedAdapter);
        mViewPager.setOffscreenPageLimit(2);

        attributes.recycle();
        setPresetIndicator(PresetIndicators.Right_Bottom);
        setPresetTransformer(pagerAnimationIndex);
        setSliderTransformDuration(pagerAnimationSpan, null);
        if (mIndicator != null) {
            mIndicator.setIndicatorVisibility(mIndicatorVisibility);
        }

        mScreenBroadcastReceiver = new ScreenBroadcastReceiver(this);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
    }

    public void setPagerAnimation(PagerAnimation pagerAnimation) {
        pagerAnimationIndex = pagerAnimation.ordinal();
        setPresetTransformer(pagerAnimationIndex);
    }

    public int getPagerAnimationSpan() {
        return pagerAnimationSpan;
    }

    public void setPagerAnimationSpan(int pagerAnimationSpan) {
        this.pagerAnimationSpan = pagerAnimationSpan;
        setSliderTransformDuration(pagerAnimationSpan, null);
    }

    public void addOnPageChangeListener(ViewPagerEx.OnPageChangeListener onPageChangeListener) {
        if (onPageChangeListener != null) {
            mViewPager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    public void removeOnPageChangeListener(ViewPagerEx.OnPageChangeListener onPageChangeListener) {
        mViewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    public void setCustomIndicator(PagerIndicator indicator) {
        if (mIndicator != null) {
            mIndicator.destroySelf();
        }
        mIndicator = indicator;
        mIndicator.setIndicatorVisibility(mIndicatorVisibility);
        mIndicator.setViewPager(mViewPager);
        mIndicator.redraw();
    }

    public <T extends BaseSliderView> void setSliders(List<T> imageContents) {
        mSliderAdapter = new SliderAdapter(mContext);
        mSliderAdapter.setSliders(imageContents);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSliderAdapter);

        mViewPager.setAdapter(wrappedAdapter);

        mIndicator.setViewPager(mViewPager);
        mIndicator.redraw();
        mIndicator.onPageSelected(0);
    }

    public <T extends BaseSliderView> void addSlider(T imageContent) {
        mSliderAdapter.addSlider(imageContent);
    }

    public <T extends BaseSliderView> void addSliders(List<T> imageContents) {
        mSliderAdapter.addSliders(imageContents);
    }

    private android.os.Handler timerHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            moveNextPosition(true);
            if (mAutoCycle) sendEmptyMessageDelayed(0, pagerAnimationDuration);
        }
    };

    public void startAutoCycle() {
        if (mAutoCycle) startCycle();
    }

    public void startCycle() {
        System.out.println("startCycle");
        stopCycle(); // 开启之前先停掉，保证消息永远只有一条
        timerHandler.sendEmptyMessageDelayed(0, pagerAnimationDuration);
    }

    /**
     * set the duration between two slider changes. the duration value must >= 500
     *
     * @param duration
     */
    public void setPagerAnimationDuration(long duration) {
        if (duration >= 500) {
            pagerAnimationDuration = duration;
            startAutoCycle();
        }
    }

    public void stopCycle() {
        timerHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                stopCycle();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startAutoCycle();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    float mPosX;
    float mCurPosX;
    boolean isIntercept = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (enableHandSlide) {
            isIntercept = super.onTouchEvent(event);
        } else {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mPosX = event.getX();
                    isIntercept = super.onTouchEvent(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    if ((Math.abs(mCurPosX - mPosX) > 10)) {
                        isIntercept = true;
                    } else {
                        isIntercept = super.onTouchEvent(event);
                    }
            }

        }
        return isIntercept;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (enableHandSlide) {
            isIntercept = super.onTouchEvent(event);
        } else {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mPosX = event.getX();
                    isIntercept = super.onTouchEvent(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    if ((Math.abs(mCurPosX - mPosX) > 10)) {
                        isIntercept = true;
                    } else {
                        isIntercept = super.onTouchEvent(event);
                    }
            }

        }
        return isIntercept;
    }

    /**
     * set ViewPager transformer.
     *
     * @param reverseDrawingOrder
     * @param transformer
     */
    public void setPagerTransformer(boolean reverseDrawingOrder, BaseTransformer transformer) {
        mViewPagerTransformer = transformer;
        mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
        mViewPager.setPageTransformer(reverseDrawingOrder, mViewPagerTransformer);
    }
    @Deprecated
    public void setDuration(long duration) {
        if (duration >= 500) {
            pagerAnimationDuration = duration;
            startAutoCycle();
        }
    }

    /**
     * set the duration between two slider changes.
     *
     * @param period
     * @param interpolator
     */
    public void setSliderTransformDuration(int period, Interpolator interpolator) {
        try {
            Field mScroller = ViewPagerEx.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), interpolator, period);
            mScroller.set(mViewPager, scroller);
        } catch (Exception e) {

        }
    }

    /**
     * preset transformers and their names
     */
    public enum PagerAnimation {
        Default("Default"),
        Accordion("Accordion"),
        Background2Foreground("Background2Foreground"),
        CubeIn("CubeIn"),
        DepthPage("DepthPage"),
        Fade("Fade"),
        FlipHorizontal("FlipHorizontal"),
        FlipPage("FlipPage"),
        Foreground2Background("Foreground2Background"),
        RotateDown("RotateDown"),
        RotateUp("RotateUp"),
        Stack("Stack"),
        Tablet("Tablet"),
        ZoomIn("ZoomIn"),
        ZoomOutSlide("ZoomOutSlide"),
        ZoomOut("ZoomOut");

        private final String name;

        private PagerAnimation(String s) {
            name = s;
        }

        public String toString() {
            return name;
        }

        public boolean equals(String other) {
            return (other == null) ? false : name.equals(other);
        }
    }

    ;

    /**
     * set a preset viewpager transformer by id.
     *
     * @param transformerId
     */
    public void setPresetTransformer(int transformerId) {
        for (PagerAnimation t : PagerAnimation.values()) {
            if (t.ordinal() == transformerId) {
                setPresetTransformer(t);
                break;
            }
        }
    }

    /**
     * set preset PagerTransformer via the name of transforemer.
     *
     * @param transformerName
     */
    public void setPresetTransformer(String transformerName) {
        for (PagerAnimation t : PagerAnimation.values()) {
            if (t.equals(transformerName)) {
                setPresetTransformer(t);
                return;
            }
        }
    }

    /**
     * Inject your custom animation into PageTransformer, you can know more details in
     * {@link BaseAnimationInterface},
     * and you can see a example in {@link DescriptionAnimation}
     *
     * @param animation
     */
    public void setCustomAnimation(BaseAnimationInterface animation) {
        mCustomAnimation = animation;
        if (mViewPagerTransformer != null) {
            mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
        }
    }

    /**
     * pretty much right? enjoy it. :-D
     *
     * @param ts
     */
    public void setPresetTransformer(PagerAnimation ts) {
        //
        // special thanks to https://github.com/ToxicBakery/ViewPagerTransforms
        //
        BaseTransformer t = null;
        switch (ts) {
            case Default:
                t = new DefaultTransformer();
                break;
            case Accordion:
                t = new AccordionTransformer();
                break;
            case Background2Foreground:
                t = new BackgroundToForegroundTransformer();
                break;
            case CubeIn:
                t = new CubeInTransformer();
                break;
            case DepthPage:
                t = new DepthPageTransformer();
                break;
            case Fade:
                t = new FadeTransformer();
                break;
            case FlipHorizontal:
                t = new FlipHorizontalTransformer();
                break;
            case FlipPage:
                t = new FlipPageViewTransformer();
                break;
            case Foreground2Background:
                t = new ForegroundToBackgroundTransformer();
                break;
            case RotateDown:
                t = new RotateDownTransformer();
                break;
            case RotateUp:
                t = new RotateUpTransformer();
                break;
            case Stack:
                t = new StackTransformer();
                break;
            case Tablet:
                t = new TabletTransformer();
                break;
            case ZoomIn:
                t = new ZoomInTransformer();
                break;
            case ZoomOutSlide:
                t = new ZoomOutSlideTransformer();
                break;
            case ZoomOut:
                t = new ZoomOutTransformer();
                break;
        }
        setPagerTransformer(true, t);
    }

    public void setIndicatorVisible(boolean visible) {
        if (visible && mIndicatorVisibility == PagerIndicator.IndicatorVisibility.Visible) return;
        if (!visible && mIndicatorVisibility == PagerIndicator.IndicatorVisibility.Invisible)
            return;

        mIndicatorVisibility = visible ? PagerIndicator.IndicatorVisibility.Visible : PagerIndicator.IndicatorVisibility.Invisible;
        if (mIndicator != null) {
            mIndicator.setIndicatorVisibility(mIndicatorVisibility);
        }
    }

    public boolean isIndicatorVisible() {
        return mIndicatorVisibility == PagerIndicator.IndicatorVisibility.Visible;
    }

    /**
     * get the {@link PagerIndicator} instance.
     * You can manipulate the properties of the indicator.
     *
     * @return
     */
    public PagerIndicator getPagerIndicator() {
        return mIndicator;
    }

    public enum PresetIndicators {
        Center_Bottom("Center_Bottom", R.id.default_center_bottom_indicator),
        Right_Bottom("Right_Bottom", R.id.default_bottom_right_indicator),
        Left_Bottom("Left_Bottom", R.id.default_bottom_left_indicator),
        Center_Top("Center_Top", R.id.default_center_top_indicator),
        Right_Top("Right_Top", R.id.default_center_top_right_indicator),
        Left_Top("Left_Top", R.id.default_center_top_left_indicator);

        private final String name;
        private final int id;

        private PresetIndicators(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String toString() {
            return name;
        }

        public int getResourceId() {
            return id;
        }
    }

    public void setPresetIndicator(PresetIndicators presetIndicator) {
        PagerIndicator pagerIndicator = (PagerIndicator) findViewById(presetIndicator.getResourceId());
        setCustomIndicator(pagerIndicator);
    }

    private InfinitePagerAdapter getWrapperAdapter() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter != null) {
            return (InfinitePagerAdapter) adapter;
        } else {
            return null;
        }
    }

    private SliderAdapter getRealAdapter() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter != null) {
            return ((InfinitePagerAdapter) adapter).getRealAdapter();
        }
        return null;
    }

    /**
     * get the current item position
     *
     * @return
     */
    public int getCurrentPosition() {

        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider ListAdapter");

        return mViewPager.getCurrentItem() % getRealAdapter().getCount();

    }

    /**
     * get current slider.
     *
     * @return
     */
    public BaseSliderView getCurrentSlider() {

        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider ListAdapter");

        int count = getRealAdapter().getCount();
        int realCount = mViewPager.getCurrentItem() % count;
        return getRealAdapter().getSliderView(realCount);
    }

    /**
     * remove  the slider at the position. Notice: It's a not perfect method, a very small bug still exists.
     */
    public void removeSliderAt(int position) {
        if (getRealAdapter() != null) {
            getRealAdapter().removeSliderAt(position);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem(), false);
        }
    }

    /**
     * remove all the sliders. Notice: It's a not perfect method, a very small bug still exists.
     */
    public void removeAllSliders() {
        SliderAdapter realAdapter = getRealAdapter();
        if (realAdapter != null) {
            realAdapter.removeAllSliders();
            mViewPager.setCurrentItem(0, false);
        }
    }

    /**
     * set current slider
     *
     * @param position
     */
    public void setCurrentPosition(int position, boolean smooth) {
        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider ListAdapter");
        if (position >= getRealAdapter().getCount()) {
            throw new IllegalStateException("Item position is not exist");
        }
        int p = mViewPager.getCurrentItem() % getRealAdapter().getCount();
        int n = (position - p) + mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(n, smooth);
    }

    public void setCurrentPosition(int position) {
        setCurrentPosition(position, true);
    }

    /**
     * move to prev slide.
     */
    public void movePrevPosition(boolean smooth) {

        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider ListAdapter");

        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, smooth);
    }

    public void movePrevPosition() {
        movePrevPosition(true);
    }

    /**
     * move to next slide.
     */
    public void moveNextPosition(boolean smooth) {

        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider ListAdapter");

        if (mViewPager != null) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, smooth);
        }
    }

    public void moveNextPosition() {
        moveNextPosition(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "绑定窗口，注册广播接收器，监听屏幕开关");
        if (mScreenBroadcastReceiver != null && filter != null) {
            getContext().registerReceiver(mScreenBroadcastReceiver, filter);
        }
        Log.d(TAG, "绑定窗口，开启轮播");
        startAutoCycle();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            Log.d(TAG, "可视，开启轮播");
            startAutoCycle();
        } else {
            Log.d(TAG, "不可视，关闭轮播");
            stopCycle();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "解绑窗口，取消注册广播接收器");
        if (mScreenBroadcastReceiver != null) {
            getContext().unregisterReceiver(mScreenBroadcastReceiver);
        }
        Log.d(TAG, "解绑窗口，停止轮播");
        stopCycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        stopCycle();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        startAutoCycle();
    }

    public void setImages(int[] resArr) {
        if (resArr == null) return;
        if (resArr.length == 0) return;

        ArrayList<BaseSliderView> sliders = new ArrayList<>();
        for (int res : resArr) {
            // 构建默认的SliderView
            Context context = getContext();
            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
            defaultSliderView.setRadius(imgRadius);
            // 设置参数
            defaultSliderView
                    .image(res)
                    .empty(defaultImgRes)
                    .error(defaultImgRes)
                    .setScaleType(ScaleType.CenterCrop.name())
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            SliderAdapter realAdapter = getRealAdapter();
                            if (realAdapter == null) return;
                            int indexOfSlider = realAdapter.getIndexOfSlider(slider);
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(indexOfSlider);
                            }
                        }
                    });

            defaultSliderView.setImageLoader(new GlideImpl(context));

            sliders.add(defaultSliderView);
        }

        setSliders(sliders);
    }

    public void setImageUrls(String[] urls) {
        if (urls == null) return;
        if (urls.length == 0) return;

        if (isSameContents(urls)) {
            Log.d(TAG, "图片的地址是一样的，无须替换");
            return;
        }

        ArrayList<BaseSliderView> sliders = new ArrayList<>();
        for (String url : urls) {
            if (TextUtils.isEmpty(url)) continue;

            // 构建默认的SliderView
            Context context = getContext();
            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
            defaultSliderView.setRadius(imgRadius);
            // 设置参数
            defaultSliderView
                    .image(url)
                    .empty(defaultImgRes)
                    .error(defaultImgRes)
                    .setScaleType(ScaleType.CenterCrop.name())
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            SliderAdapter realAdapter = getRealAdapter();
                            if (realAdapter == null) return;
                            int indexOfSlider = realAdapter.getIndexOfSlider(slider);
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(indexOfSlider);
                            }
                        }
                    });

            defaultSliderView.setImageLoader(new GlideImpl(context));

            sliders.add(defaultSliderView);
        }

        setSliders(sliders);
    }

    private boolean isSameContents(String[] urls) {
        if (urls == null) return false;
        int length = urls.length;
        if (length == 0) return false;

        SliderAdapter realAdapter = getRealAdapter();
        if (realAdapter == null) return false;

        ArrayList<BaseSliderView> imageContents = realAdapter.getImageContents();
        if (imageContents == null) return false;

        int size = imageContents.size();
        if (size == 0) return false;

        if (size != length) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            String url = imageContents.get(i).getUrl();
            if (url == null) return false;
            boolean sameUrl = url.equals(urls[i]);
            if (!sameUrl) {
                return false;
            }
        }
        return true;
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private float ratio = Float.NaN;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int actualWidthMeasureSpec = widthMeasureSpec;
        int actualHeightMeasureSpec = heightMeasureSpec;

        if (!Float.isNaN(ratio)) {
            final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST) {
                final int width = MeasureSpec.getSize(widthMeasureSpec);
                int actualHeight = (int) (width / ratio);
                actualHeightMeasureSpec = MeasureSpec.makeMeasureSpec(actualHeight, MeasureSpec.EXACTLY);
            }
        }

        super.onMeasure(actualWidthMeasureSpec, actualHeightMeasureSpec);
    }

    public boolean isAutoCycle() {
        return mAutoCycle;
    }

    public void setAutoCycle(boolean autoCycle) {
        if (mAutoCycle != autoCycle) {
            mAutoCycle = autoCycle;
            if (mAutoCycle) {
                startAutoCycle();
            }
        }
    }

    public boolean isEnableHandSlide() {
        return enableHandSlide;
    }

    public void setEnableHandSlide(boolean enableHandSlide) {
        this.enableHandSlide = enableHandSlide;
    }

    private static class ScreenBroadcastReceiver extends BroadcastReceiver {

        private SliderLayout sliderLayout = null;

        public ScreenBroadcastReceiver(SliderLayout sliderLayout) {
            this.sliderLayout = sliderLayout;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (sliderLayout != null) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d(TAG, "屏幕亮起，开启轮播");
                    sliderLayout.startAutoCycle();
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d(TAG, "屏幕关闭，停止轮播");
                    sliderLayout.stopCycle();
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d(TAG, "用户存在，开启轮播");
                    sliderLayout.startAutoCycle();
                }
            }
        }
    }

    // 设置图片圆角
    public void setImgRadius(int radius) {
        if (this.imgRadius != radius) {
            this.imgRadius = radius;
            SliderAdapter realAdapter = getRealAdapter();
            if (realAdapter != null) {
                ArrayList<BaseSliderView> imageContents = realAdapter.getImageContents();
                if (imageContents != null) {
                    for (BaseSliderView item : imageContents) {
                        if (item instanceof DefaultSliderView) {
                            ((DefaultSliderView) item).setRadius(radius);
                        }
                    }
                }
            }
        }
    }

    // 设置图片圆角,此方法废弃
    @Deprecated
    public void setRadius(int radius) {
        setImgRadius(radius);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public float getRatio() {
        return ratio;
    }

    /**
     * 设置指示器的padding，只有值不一样才设置新的.
     *
     * @param left   左padding值.
     * @param top    上padding值.
     * @param right  右padding值.
     * @param bottom 下padding值.
     */
    public void setIndicatorPadding(int left, int top, int right, int bottom) {
        PagerIndicator pagerIndicator = getPagerIndicator();
        if (pagerIndicator != null) {
            int paddingLeft = pagerIndicator.getPaddingLeft();
            int paddingTop = pagerIndicator.getPaddingTop();
            int paddingRight = pagerIndicator.getPaddingRight();
            int paddingBottom = pagerIndicator.getPaddingBottom();
            if (left != paddingLeft || top != paddingTop || right != paddingRight || bottom != paddingBottom) {
                pagerIndicator.setPadding(left, top, right, bottom);
            }
        }
    }
}
