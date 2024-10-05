package com.pasc.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 上滚广告.
 */
public class PascUpRollView extends LinearLayout implements OnLayoutChangeListener {

    private static final String TAG = "PascUpRollView";

    private static final int WHAT_SCROLL = 0;
    /**
     * 默认滚动耗时
     */
    private static final int DEFAULT_DURATION = 1000;
    /**
     * 默认滚动间隔
     */
    private static final int DEFAULT_INTERVAL = 3000;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 正在展示的View的Index
     */
    private int mCurrentItem = 0;
    /**
     * 是否需要滚动
     */
    private boolean mIsAutoScrolled = false;
    /**
     * 是否需要增删item-在滚动完成之后，删除上一个item，添加下一个item
     */
    private boolean mNeedChangeItem = false;

    private int mItemHeight;
    private int mItemWidth;

    /**
     * 滚动的耗时
     */
    private int rollDuration = DEFAULT_DURATION;
    /**
     * 滚动的间隔
     */
    private int rollInterval = DEFAULT_INTERVAL;

    private Scroller mScroller;
    private BaseAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    public PascUpRollView(Context context) {
        this(context, null);
    }

    public PascUpRollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PascUpRollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new Scroller(context);
        //获得这个控件对应的属性。
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.UpRollView);

        rollInterval = a.getInteger(R.styleable.PascUpRollView_roll_interval, DEFAULT_INTERVAL);
        rollDuration = a.getInteger(R.styleable.PascUpRollView_roll_duration, DEFAULT_DURATION);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, getDefaultSize(0, MeasureSpec.AT_MOST));
            mItemWidth = Math.max(child.getMeasuredWidth(), mItemWidth);
            mItemHeight = child.getMeasuredHeight();
        }
        setMeasuredDimension(widthMeasureSpec, mItemHeight);
    }

    public void notifyDataChanged() {
        setItemClickListener();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            return;
        }
        if (mScroller.isFinished() && mNeedChangeItem) {
            changeViewItem();
            mNeedChangeItem = false;
        }
        super.computeScroll();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
        removeOnLayoutChangeListener(this);
        scrollTo(0, 0);
        sendScrollMessage(rollInterval);
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        setItemClickListener();
    }

    public void setItemHeight(int height) {
        this.mItemHeight = height;
        invalidate();
    }

    /**
     * 开始自动轮播
     */
    public void startAutoScroll() {
        if (mAdapter == null) return;
        stopAutoScroll();
        removeAllViewsInLayout();
        mCurrentItem = 0;
        mIsAutoScrolled = true;

        int count = mAdapter.getCount();
        if (count > 1) {
            addView(mAdapter.getView(0));
            addView(mAdapter.getView(1));
            sendScrollMessage(rollInterval);
        } else if (count == 1) {
            addView(mAdapter.getView(0));
        } else {
            // do nothing
        }
    }

    public void stopAutoScroll() {
        mIsAutoScrolled = false;
        mHandler.removeMessages(WHAT_SCROLL);
    }

    public boolean isScrolling() {
        return mIsAutoScrolled;
    }

    public int getRollDuration() {
        return rollDuration;
    }

    /**
     * 设置滚动耗时
     */
    public void setRollDuration(int rollDuration) {
        this.rollDuration = rollDuration;
    }

    public int getRollInterval() {
        return rollInterval;
    }

    /**
     * 设置滚动间隔
     */
    public void setRollInterval(int rollInterval) {
        this.rollInterval = rollInterval;
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 设置适配器
     */
    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
        mAdapter.registerObservable(this);
        setItemClickListener();
    }

    private void sendScrollMessage(long delayTimeInMills) {
        mHandler.removeMessages(WHAT_SCROLL);
        mHandler.sendEmptyMessageDelayed(WHAT_SCROLL, delayTimeInMills);
    }

    private void changeViewItem() {
        addOnLayoutChangeListener(this);
        int count = mAdapter.getCount();
        if (count > 1) {
            removeView(mAdapter.getView(mCurrentItem % count));
            mCurrentItem++;
            addNextItemView(mCurrentItem + 1);
        }
    }

    private void addNextItemView(int index) {
        View view = mAdapter.getView(index % mAdapter.getCount());
        if (view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        addView(view);
    }

    private void setItemClickListener() {
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View itemView = mAdapter.getView(i);
                itemView.setOnClickListener(mOnClickListener);
            }
        }
    }

    public boolean isNeedChangeItem() {
        return mNeedChangeItem;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_SCROLL:
                    mNeedChangeItem = true;
                    int height = getHeight();
                    mScroller.startScroll(0, 0, 0, height, rollDuration);
                    invalidate();
                    break;
            }
            return false;
        }
    });

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, mAdapter.getItemId(v));
            }
        }
    };

    public abstract static class BaseAdapter<T> {

        protected Context mContext;
        protected List<T> mDatas;
        protected List<View> mViews;
        protected PascUpRollView mObservable;

        public BaseAdapter(Context context, List<T> dataList) {
            this.mContext = context;
            this.mDatas = dataList;
            mViews = new ArrayList<>();
        }

        public void registerObservable(PascUpRollView observable) {
            this.mObservable = observable;
        }

        public void setDataList(List<T> dataList) {
            this.mDatas = dataList;
            mViews.clear();
            notifyDataChanged();
        }

        public void addDataList(List<T> dataList) {
            this.mDatas.addAll(dataList);
            notifyDataChanged();
        }

        public void notifyDataChanged() {
            mObservable.notifyDataChanged();
        }

        public int getCount() {
            if (mDatas != null) {
                return mDatas.size();
            } else {
                return 0;
            }
        }

        public int getItemId(View v) {
            return mViews.indexOf(v);
        }

        public abstract View getView(int position);

        public T getItem(int position) {
            if (mDatas == null || mDatas.size() - 1 < position) {
                return null;
            }
            return mDatas.get(position);
        }
    }

    public static class SimpleTextAdapter extends BaseAdapter<String> {

        public SimpleTextAdapter(Context context, List<String> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(int position) {
            if (mDatas == null || mDatas.isEmpty()) {
                return null;
            }
            TextView tv;
            if (mViews.size() == position) {
                tv = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.item_simple_text, null);
                tv.setText(mDatas.get(position));
                mViews.add(tv);
            } else {
                tv = (TextView) mViews.get(position);
            }
            return tv;
        }
    }

    public static class MultiLineTextAdapter extends PascUpRollView.BaseAdapter<List<String>> {

        int lineCount = 2;
        int lineGap = 0;
        int lineLayoutId = R.layout.pasc_up_roll_view_line_text_view;

        public int getLineGap() {
            return lineGap;
        }

        public void setLineGap(int lineGap) {
            this.lineGap = lineGap;
        }

        public int getLineLayoutId() {
            return lineLayoutId;
        }

        public void setLineLayoutId(int lineLayoutId) {
            this.lineLayoutId = lineLayoutId;
        }

        public int getLineCount() {
            return lineCount;
        }

        public void setLineCount(int lineCount) {
            this.lineCount = lineCount;
        }

        public MultiLineTextAdapter(Context context, List<List<String>> dataList) {
            super(context, dataList);
        }

        @Override
        public View getView(int position) {
            if (mDatas == null || mDatas.isEmpty()) {
                return null;
            }
            View view;
            if (mViews.size() == position) {
                View root = (View) LayoutInflater.from(mContext)
                        .inflate(R.layout.pasc_up_roll_view_multi_line_text_view, null);
                view = root;

                List<String> item = getItem(position);
                int size = item.size();

                LinearLayout virtualContainer = root.findViewById(R.id.virtualContainer);
                LinearLayout realContainer = root.findViewById(R.id.realContainer);

                for (int i = 0; i < lineCount; i++) {
                    View viewVirtual = LayoutInflater.from(mContext)
                            .inflate(lineLayoutId, null);

                    if (i > 0) {
                        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, lineGap, 0, 0);
                        viewVirtual.setLayoutParams(layoutParams);
                    }

                    virtualContainer.addView(viewVirtual);


                    if (i < size) {
                        View viewReal = (View) LayoutInflater.from(mContext)
                                .inflate(lineLayoutId, null);

                        if (i > 0) {
                            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0, lineGap, 0, 0);
                            viewReal.setLayoutParams(layoutParams);
                        }

                        TextView textViewReal = viewReal.findViewById(R.id.upRollTextView);
                        textViewReal.setText(item.get(i % size));
                        realContainer.addView(viewReal);
                    }
                }
                mViews.add(view);
            } else {
                view = mViews.get(position);
            }
            return view;
        }
    }
}
