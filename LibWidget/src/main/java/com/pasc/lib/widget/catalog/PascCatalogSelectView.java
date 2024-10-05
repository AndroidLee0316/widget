package com.pasc.lib.widget.catalog;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.catalog.bean.MultiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单选择view
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/24
 */
public class PascCatalogSelectView extends FrameLayout {
    private Catalog mCatalog;
    /** 左边宽度 */
    private int mLeftWidth;
    /** 左边List */
    private RecyclerView mLeftRecyclerView;
    /** 右边List */
    private RecyclerView mRightRecyclerView;
    /** 单个条目点击监听 */
    private OnSingleClickListener mOnSingleClickListener;
    /** 两个条目点击监听 */
    private OnMultiClickListener mOnMultiClickListener;
    /** 左边adapter */
    private CatalogSelectAdapter mLeftAdapter;
    /** 右边adapter */
    private CatalogSelectAdapter mRightAdapter;

    private List<CharSequence> mLeftDatas = new ArrayList<>();
    private List<MultiBean> mMultiDatas = new ArrayList<>();

    public PascCatalogSelectView(@NonNull Context context) {
        this(context, null);
    }

    public PascCatalogSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PascCatalogSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.pasc_view_catolog_select, this, true);
        initView(view);
        setAdapter(context);
    }

    private void initView(View view) {
        mLeftRecyclerView = view.findViewById(R.id.left_list);
        mRightRecyclerView = view.findViewById(R.id.right_list);
        setLayoutParams();
    }

    private void setAdapter(@NonNull Context context) {
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLeftAdapter = new CatalogSelectAdapter(mLeftDatas, true, mCatalog);
        mLeftAdapter.setCurrentSelectPosition(0);
        mLeftAdapter.bindToRecyclerView(mLeftRecyclerView);

        mRightRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRightAdapter = new CatalogSelectAdapter(new ArrayList<CharSequence>(), false, mCatalog);
        mRightAdapter.setCurrentSelectPosition(-1);
        mRightAdapter.bindToRecyclerView(mRightRecyclerView);

        mLeftAdapter.setOnItemClickListener(onLeftItemClickListener);
        mRightAdapter.setOnItemClickListener(onRightItemClickListener);
    }

    private void setLayoutParams() {
        ViewGroup.LayoutParams leftParams = mLeftRecyclerView.getLayoutParams();
        leftParams.width = mLeftWidth;
        mLeftRecyclerView.setLayoutParams(leftParams);

        ViewGroup.LayoutParams rightParams = mRightRecyclerView.getLayoutParams();
        rightParams.width = DensityUtils.getScreenWidth(getContext()) - mLeftWidth;
        mRightRecyclerView.setLayoutParams(rightParams);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.PascCatalogSelectView, defStyleAttr, 0);
        int normalTextColor = array.getColor(R.styleable.PascCatalogSelectView_cs_normal_text_color, Color.parseColor("#333333"));
        int selectedTextColor = array.getColor(R.styleable.PascCatalogSelectView_cs_selected_text_color, Color.parseColor("#2AA6FA"));
        int normalBgColor = array.getColor(R.styleable.PascCatalogSelectView_cs_bg_color_item_normal, Color.parseColor("#FFFFFF"));
        int selectedBgColor = array.getColor(R.styleable.PascCatalogSelectView_cs_bg_color_item_selected, Color.parseColor("#f4f4f4"));
        int itemHeight = array.getDimensionPixelSize(R.styleable.PascCatalogSelectView_cs_item_height, DensityUtils.dip2px(getContext(), 45));
        int textSize = array.getDimensionPixelSize(R.styleable.PascCatalogSelectView_android_textSize, DensityUtils.sp2px(15));
        mCatalog = new Catalog(normalTextColor, selectedTextColor, normalBgColor, selectedBgColor, itemHeight, textSize);
        mLeftWidth = array.getDimensionPixelSize(R.styleable.PascCatalogSelectView_cs_left_width, DensityUtils.dip2px(getContext(), 132));
        array.recycle();
    }

    /**
     * 左边item点击监听
     */
    private BaseQuickAdapter.OnItemClickListener onLeftItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (position == mLeftAdapter.getCurrentSelectPosition()) {
                //两次点击同一item
                return;
            }
            mLeftAdapter.setSelect(position);

            if (mRightRecyclerView.getVisibility() == VISIBLE) {
                List<CharSequence> rightDatas = mMultiDatas.get(position).getRightDatas();
                mRightAdapter.setCurrentSelectPosition(-1);
                mRightAdapter.setNewData(rightDatas);
                mRightRecyclerView.scrollToPosition(0);
            }
            if (mOnSingleClickListener != null) {
                mOnSingleClickListener.onClick(position);
            }
            if (mOnMultiClickListener != null) {
                mOnMultiClickListener.onLeftItemClick(position);
            }
        }
    };

    /**
     * 右边item点击监听
     */
    private BaseQuickAdapter.OnItemClickListener onRightItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            mRightAdapter.setSelect(position);
            if (mOnMultiClickListener != null) {
                mOnMultiClickListener.onMultiClick(mLeftAdapter.getCurrentSelectPosition(), mRightAdapter.getCurrentSelectPosition());
            }
        }
    };

    /**
     * 设置单个条目数据
     *
     * @param datas          数据
     * @param selectPosition 选中的位置
     * @param listener       监听
     */
    public void setSingleData(List<CharSequence> datas, int selectPosition, OnSingleClickListener listener) {
        if (datas == null) {
            throw new IllegalArgumentException("leftDatas不能为null！！！！");
        }
        mOnSingleClickListener = listener;
        mRightRecyclerView.setVisibility(GONE);
        mLeftAdapter.setCurrentSelectPosition(selectPosition);
        setLeftLayoutParams();
        mLeftDatas.clear();
        mLeftDatas.addAll(datas);
        mLeftAdapter.notifyDataSetChanged();
    }

    private void setLeftLayoutParams() {
        ViewGroup.LayoutParams layoutParams = mLeftRecyclerView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mLeftRecyclerView.setLayoutParams(layoutParams);
    }

    /**
     * 设置多条目数据
     *
     * @param multiDatas     数据
     * @param selectPosition 选中的位置
     * @param listener       监听
     */
    public void setMultiData(List<MultiBean> multiDatas, int selectPosition, OnMultiClickListener listener) {
        if (multiDatas == null) {
            throw new IllegalArgumentException("multiData参数不能为null！！！！");
        }
        mOnMultiClickListener = listener;
        mLeftRecyclerView.setVisibility(VISIBLE);
        mRightRecyclerView.setVisibility(VISIBLE);
        setLayoutParams();
        mLeftAdapter.setCurrentSelectPosition(selectPosition);
        mRightAdapter.setCurrentSelectPosition(-1);
        mMultiDatas.clear();
        mMultiDatas.addAll(multiDatas);
        mLeftDatas.clear();
        for (MultiBean bean : multiDatas) {
            mLeftDatas.add(bean.getLeftName());
        }
        mRightAdapter.setNewData(multiDatas.get(0).getRightDatas());
        mLeftAdapter.notifyDataSetChanged();
        mRightAdapter.notifyDataSetChanged();
    }

    /**
     * 设置正常状态下文字颜色
     *
     * @param normalTextColor 颜色
     */
    public void setNormalTextColor(int normalTextColor) {
        mCatalog.setNormalTextColor(normalTextColor);
    }

    /**
     * 设置选中状态下文字颜色
     *
     * @param selectedTextColor 颜色
     */
    public void setSelectedTextColor(int selectedTextColor) {
        mCatalog.setSelectedTextColor(selectedTextColor);
    }

    /**
     * 设置正常状态下item背景颜色
     *
     * @param normalBgColor 颜色
     */
    public void setNormalBgColor(int normalBgColor) {
        mCatalog.setNormalBgColor(normalBgColor);
    }

    /**
     * 设置选中状态下item背景颜色
     *
     * @param selectedBgColor 颜色
     */
    public void setSelectedBgColor(int selectedBgColor) {
        mCatalog.setSelectedBgColor(selectedBgColor);
    }

    /**
     * 设置item的高度
     *
     * @param itemHeight 颜色
     */
    public void setItemHeight(int itemHeight) {
        mCatalog.setItemHeight(itemHeight);
    }

    /**
     * 设置文字大小
     *
     * @param textSizePx 文字大小，单位为px
     */
    public void setTextSizePx(int textSizePx) {
        mCatalog.setTextSize(textSizePx);
    }

    /**
     * 通知界面刷新数据
     */
    public void notifyDataSetChanged() {
        mLeftAdapter.notifyDataSetChanged();
        mRightAdapter.notifyDataSetChanged();
    }

    /**
     * 多个条目时点击监听
     */
    public interface OnMultiClickListener {
        /**
         * 监听回调
         *
         * @param leftPosition  左边item被点击的位置
         * @param rightPosition 右边item被点击的位置
         */
        void onMultiClick(int leftPosition, int rightPosition);

        /**
         * 左边item点击监听回调
         *
         * @param position 被点击的位置
         */
        void onLeftItemClick(int position);
    }

    public interface OnSingleClickListener {
        /**
         * 监听回调
         *
         * @param position 被点击的位置
         */
        void onClick(int position);
    }

}