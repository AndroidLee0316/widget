package com.pasc.lib.widget.loaderview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pasc.lib.widget.R;

public class LoaderRecyclerView extends RelativeLayout {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler(Looper.getMainLooper());

    public LoaderRecyclerView(Context context) {
        this(context, null);
    }

    public LoaderRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_load, null);
        swipeRefreshLayout = view.findViewById(R.id.layout_srlLoader);
        swipeRefreshLayout.setColorSchemeResources(R.color.line_loader_recycler_view_scheme_color);
        swipeRefreshLayout.setEnabled(true);
        recyclerView = view.findViewById(R.id.layout_rlDatas);
        addView(view);
    }

    public LoaderRecyclerView setLinearLayoutManager(Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return this;
    }

    public LoaderRecyclerView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        return this;
    }

    public LoaderRecyclerView setAdapter(BaseQuickAdapter adapter) {
        recyclerView.setAdapter(adapter);
        return this;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * @param refreshing Whether or not the view should show refresh progress.
     */
    public void setRefreshing(final boolean refreshing) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refreshing);
            }
        });
    }

    public boolean isRefreshing() {
        return swipeRefreshLayout.isRefreshing();
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 是否具有下拉刷新的功能
     *
     * @param enable true：有下拉刷新的功能， false：没有下拉刷新的功能；
     */
    public void setRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    public boolean isRefreshEnable() {
        return swipeRefreshLayout.isEnabled();
    }

    /**
     * recyclerView数据是否满屏
     * true:铺满全屏，false：没有铺满全屏
     */
    boolean isFullPage(final IsFullPageListener isFullPageListener) {
        RecyclerView recyclerView = getRecyclerView();
        final BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
        if (baseQuickAdapter == null) return false;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) return false;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    isFullPageListener.isFullPageListerner(linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 < baseQuickAdapter.getItemCount());
                }
            });
            return linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 < baseQuickAdapter.getItemCount();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            final int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    isFullPageListener.isFullPageListerner(getTheBiggestNumber(positions) + 1 < baseQuickAdapter.getItemCount());
                }
            });
            return getTheBiggestNumber(positions) + 1 < baseQuickAdapter.getItemCount();
        }
        return false;
    }

    private int getTheBiggestNumber(int[] numbers) {
        int tmp = -1;
        if (numbers == null || numbers.length == 0) {
            return tmp;
        }
        for (int num : numbers) {
            if (num > tmp) {
                tmp = num;
            }
        }
        return tmp;
    }

    public interface IsFullPageListener {
        void isFullPageListerner(boolean isFullPage);
    }
}
