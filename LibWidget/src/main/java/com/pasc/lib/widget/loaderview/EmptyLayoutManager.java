package com.pasc.lib.widget.loaderview;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.CustomLoadMoreView;
import com.pasc.lib.widget.ICallBack;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class EmptyLayoutManager<ITEM, MODEL> {
    public static final int DEFAULT_PAGE_SIZE = 20;
    //初始化
    public static final int ACTION_INITIAL = -1;
    //下拉刷新
    public static final int ACTION_REFRESH = 0;
    //上拉加载
    public static final int ACTION_LOADMORE = 1;
    //网络异常时重新加载
    public static final int ACTION_RETRY = 2;
    //从数据库获取数据
    public static final int ACTION_CACHE = 3;
    private BaseQuickAdapter<ITEM, BaseViewHolder> baseQuickAdapter;
    private LoaderRecyclerView loaderRecyclerView;
    private IEmptyLayoutConfig emptyLayoutConfig;
    private DataLoadListener<MODEL> dataLoadListener;
    //数据获取的方式： 上拉加载/下拉刷新/网络异常重新加载
    private int dataGetAction = ACTION_INITIAL;
    //分页请求数据时请求数量
    private int pageSize = DEFAULT_PAGE_SIZE;
    private CustomLoadMoreView customLoadMoreView;
    private List<ITEM> cacheDatas = new ArrayList<>();

    public EmptyLayoutManager(BaseQuickAdapter<ITEM, BaseViewHolder> baseQuickAdapter, LoaderRecyclerView loaderRecyclerView, DataLoadListener<MODEL> dataLoadListener) {
        this.baseQuickAdapter = baseQuickAdapter;
        this.loaderRecyclerView = loaderRecyclerView;
        emptyLayoutConfig = new EmptyLayoutConfigImpl(loaderRecyclerView.getContext());
        this.baseQuickAdapter.setEmptyView(emptyLayoutConfig.getEmptyLayout());
        this.baseQuickAdapter.setLoadMoreView(customLoadMoreView = new CustomLoadMoreView());
        this.baseQuickAdapter.setEnableLoadMore(true);
        this.baseQuickAdapter.bindToRecyclerView(this.loaderRecyclerView.getRecyclerView());
//        this.baseQuickAdapter.disableLoadMoreIfNotFullPage();
        initDataLoadListener(dataLoadListener);
    }

    public void onSuccess(MODEL model) {
        dataLoadListener.onSuccess(model);
    }

    /**
     * 填充数据并展示不同视图
     *
     * @param datas 填充的数据
     */
    public void setData(List<ITEM> datas) {
        switch (dataGetAction) {
            case ACTION_REFRESH:
                addDataForRefresh(datas);
                break;
            case ACTION_LOADMORE:
                addDataForLoadMore(datas);
                break;
            case ACTION_RETRY:
                addDataForRetry(datas);
                break;
        }
        dataGetAction = ACTION_INITIAL;
    }

    /**
     * 填充数据并展示不同视图,如果当前数据获取是非数据库缓存获取，直接调用setData(List<ITEM> datas)即可
     *
     * @param datas 填充的数据
     */
    public void setData(@DataRequestActionAnno int action, List<ITEM> datas) {
        if (action == ACTION_CACHE) {
            addDataForCache(datas);
        } else {
            dataGetAction = action;
            setData(datas);
        }
    }

    /**
     * @param datas 从本地数据库获取数据之后进行数据填充
     */
    private void addDataForCache(List<ITEM> datas) {
//        if (datas == null) {
//            customLoadMoreView.setLoadMoreStatus(CustomLoadMoreView.STATUS_END);
//            return;
//        }
        if (!datas.isEmpty()) {
            cacheDatas.addAll(datas);
        }
//        if (datas.size() < (pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize)) {
//            customLoadMoreView.setLoadMoreStatus(CustomLoadMoreView.STATUS_END);
//            customLoadMoreView.setLoadMoreStatus(CustomLoadMoreView.STATUS_LOADING);
//        }
    }

    /**
     * 校验操作，该方法主要用于：
     * 1. 第一次进入界面进行数据请求；
     * 2. 外部条件搜索进行数据请求；
     * false: 正在进行下拉刷新/上拉加载
     * true : 可以进行数据请求操作
     */
    public boolean refreshCheck() {
        //如果正在进行上拉加载，此时请求不做处理
        if (isDataLoading()) {
            return false;
        }
        dataGetAction = ACTION_RETRY;
        cacheDatas.clear();
        baseQuickAdapter.getData().clear();
        baseQuickAdapter.notifyDataSetChanged();
        emptyLayoutConfig.showLoadingLayout();
        return true;
    }

    /**
     * 操作的异常：上拉加载/下拉刷新/网络异常重试
     */
    public void onError() {
        boolean isAdapterDatasEmpty = cacheDatas.isEmpty();
        switch (dataGetAction) {
            case ACTION_REFRESH:
                loaderRecyclerView.setRefreshing(false);
                if (isAdapterDatasEmpty) {
                    emptyLayoutConfig.showErrorLayout();
                } else {
                    baseQuickAdapter.addData(cacheDatas);
                    if (cacheDatas.size() < (pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize)) {
                        showRightRemindWithDataObtain();
                    } else {
                        baseQuickAdapter.loadMoreComplete();
                    }
                }
                break;
            case ACTION_LOADMORE:
                baseQuickAdapter.loadMoreFail();
                break;
            case ACTION_RETRY:
                if (isAdapterDatasEmpty) {
                    emptyLayoutConfig.showErrorLayout();
                } else {
                    baseQuickAdapter.addData(cacheDatas);
                    emptyLayoutConfig.setLoadingLayoutIsVisible(false);
                    if (cacheDatas.size() < (pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize)) {
                        showRightRemindWithDataObtain();
                    } else {
                        baseQuickAdapter.loadMoreComplete();
                    }
                }
                break;
        }
        dataGetAction = ACTION_INITIAL;
        baseQuickAdapter.notifyDataSetChanged();
        dataLoadListener.onError();
    }

    /**
     * @return 获取与Adapter绑定的数据集
     */
    public List<ITEM> getData() {
        return baseQuickAdapter.getData();
    }

    /**
     * 初始化各种事件，如果不需要某一个事件，需要调用对应的setXXXEnable方法进行单独设置；
     * setLoadMoreEnable: 设置是否具有上拉加载的能力
     * setRefreshEnable:  设置是否具有下拉刷新的能力
     * setRetryEnable:    设置是否具有重试的能力
     */
    private void initDataLoadListener(final DataLoadListener<MODEL> dataLoadListener) {
        if (dataLoadListener == null) {
            this.dataLoadListener = new EmptyDataLoadListener<>();
        } else {
            this.dataLoadListener = dataLoadListener;
        }
        emptyLayoutConfig.setCallBack(new ICallBack() {
            @Override
            public void callBack() {
                if (isDataLoading()) {
                    return;
                }
                dataGetAction = ACTION_RETRY;
                cacheDatas.clear();
                baseQuickAdapter.getData().clear();
                baseQuickAdapter.notifyDataSetChanged();
                emptyLayoutConfig.showLoadingLayout();
                dataLoadListener.retryData();
            }
        });
        baseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isDataLoading()) {
                    return;
                }
                dataGetAction = ACTION_LOADMORE;
                emptyLayoutConfig.showLoadingLayout();
                dataLoadListener.loadData();
            }
        }, loaderRecyclerView.getRecyclerView());

        if (loaderRecyclerView.isRefreshEnable()) {
            loaderRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (isDataLoading()) {
                        loaderRecyclerView.setRefreshing(false);
                        return;
                    }
                    dataGetAction = ACTION_REFRESH;
                    cacheDatas.clear();
                    baseQuickAdapter.getData().clear();
                    baseQuickAdapter.notifyDataSetChanged();
                    dataLoadListener.refreshData();
                }
            });
        }
    }

    /**
     * @param loadMoreEnable true: 有上拉加载的能力， false：不具备上拉加载的能力
     */
    public EmptyLayoutManager<ITEM, MODEL> setLoadMoreEnable(boolean loadMoreEnable) {
        baseQuickAdapter.setEnableLoadMore(loadMoreEnable);
        return this;
    }

    /**
     * @param refreshEnable true: 有下拉刷新的能力， false： 不具备下拉刷新的能力
     */
    public EmptyLayoutManager<ITEM, MODEL> setRefreshEnable(boolean refreshEnable) {
        loaderRecyclerView.setRefreshEnable(refreshEnable);
        return this;
    }

    /**
     * @param retryEnable true: 有重新获取数据的能力， false： 不具备重新获取数据的能力
     */
    public EmptyLayoutManager<ITEM, MODEL> setRetryEnable(boolean retryEnable) {
        if (!retryEnable) {
            emptyLayoutConfig.setCallBack(null);
        }
        return this;
    }

    /**
     * @param datas 刷新数据之后的数据填充
     */
    private void addDataForRefresh(List<ITEM> datas) {
        loaderRecyclerView.setRefreshing(false);
        if (datas != null && !datas.isEmpty()) {
            baseQuickAdapter.addData(datas);
        } else {
            baseQuickAdapter.addData(cacheDatas);
        }
        List<ITEM> adapterDatas = baseQuickAdapter.getData();
        if (adapterDatas.isEmpty()) {
            emptyLayoutConfig.showEmptyLayout();
        } else if (baseQuickAdapter.getData().size() < (pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize)) {
            showRightRemindWithDataObtain();
        } else {
            if (customLoadMoreView.isLoadEndGone()) {
                customLoadMoreView.setLoadMoreEndGone(false);
            }
            baseQuickAdapter.loadMoreComplete();
        }
    }

    /**
     * @param datas 上拉加载数据之后的数据填充
     */
    private void addDataForLoadMore(List<ITEM> datas) {
        if (datas != null) {
            baseQuickAdapter.addData(datas);
        }
        if (datas == null || datas.size() < (pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize)) {
            showRightRemindWithDataObtain();
        } else {
            baseQuickAdapter.loadMoreComplete();
        }
    }

    /**
     * @param datas 网络异常数据再次请求之后的数据填充
     */
    private void addDataForRetry(List<ITEM> datas) {
        if (datas != null && !datas.isEmpty()) {
            baseQuickAdapter.addData(datas);
        } else {
            baseQuickAdapter.addData(cacheDatas);
        }
        List<ITEM> adapterDatas = baseQuickAdapter.getData();
        if (adapterDatas.isEmpty()) {
            emptyLayoutConfig.showEmptyLayout();
        } else {
            emptyLayoutConfig.setLoadingLayoutIsVisible(false);
            if (baseQuickAdapter.getData().size() < (pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize)) {
                showRightRemindWithDataObtain();
            } else {
                if (customLoadMoreView.isLoadEndGone()) {
                    customLoadMoreView.setLoadMoreEndGone(false);
                }
                baseQuickAdapter.loadMoreComplete();
            }
        }
    }

    public EmptyLayoutManager<ITEM, MODEL> setCustomEmptyView(@DrawableRes int emptyIcon, String emptyContent) {
        emptyLayoutConfig.setCustomEmptyView(emptyIcon, emptyContent);
        return this;
    }

    public EmptyLayoutManager<ITEM, MODEL> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public EmptyLayoutManager<ITEM, MODEL> setMaxPageSize() {
        this.pageSize = Integer.MAX_VALUE;
        return this;
    }

    /**
     * @param flag true: VISIBLE, false: GONE
     */
    public EmptyLayoutManager<ITEM, MODEL> setLoadingLayoutVisible(boolean flag) {
        emptyLayoutConfig.setLoadingLayoutIsVisible(flag);
        return this;
    }

    /**
     * 显示提醒布局：
     * 有加载更多的功能且数据满屏
     */
    private void showRightRemindWithDataObtain() {
        if (customLoadMoreView.isLoadEndGone()) {
            customLoadMoreView.setLoadMoreEndGone(false);
        }
        customLoadMoreView.setLoadEndViewVisible(true);
        baseQuickAdapter.loadMoreEnd(false);
        isFullPage();
    }

    /**
     * 是否正在进行数据加载
     * true：正在加载
     * false：没有加载
     */
    private boolean isDataLoading() {
        return dataGetAction == ACTION_REFRESH
                || dataGetAction == ACTION_LOADMORE
                || dataGetAction == ACTION_RETRY
                || dataGetAction == ACTION_CACHE;
    }

    private void isFullPage() {
        loaderRecyclerView.isFullPage(isFullPageListener);
    }

    private LoaderRecyclerView.IsFullPageListener isFullPageListener = new LoaderRecyclerView.IsFullPageListener() {
        @Override
        public void isFullPageListerner(boolean isFullPage) {
//            if (customLoadMoreView.isLoadEndGone()) {
//                customLoadMoreView.setLoadMoreEndGone(false);
//            }
            boolean loadMoreEnd = !(baseQuickAdapter.isLoadMoreEnable() && isFullPage);
            customLoadMoreView.setLoadEndViewVisible(loadMoreEnd);
            baseQuickAdapter.loadMoreEnd(loadMoreEnd);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACTION_REFRESH, ACTION_RETRY, ACTION_LOADMORE, ACTION_CACHE})
    @interface DataRequestActionAnno {
    }
}
