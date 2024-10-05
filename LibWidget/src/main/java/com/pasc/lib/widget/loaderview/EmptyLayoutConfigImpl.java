package com.pasc.lib.widget.loaderview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.pasc.lib.widget.EmptyView;
import com.pasc.lib.widget.ICallBack;

public class EmptyLayoutConfigImpl implements IEmptyLayoutConfig {
    private EmptyView emptyView;
    private ICallBack callBack;

    public EmptyLayoutConfigImpl(Context context) {
        emptyView = new EmptyView(context);
    }

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setCustomEmptyView(@DrawableRes int emptyIcon, String emptyContent) {
        emptyView.setCustomEmptyView(emptyIcon, emptyContent);
    }

    @Override
    public void showEmptyLayout() {
        emptyView.showDefaultEmptyLayout();
    }

    @Override
    public void showLoadingLayout() {
        emptyView.showDefaultLoadingLayout();
    }

    public EmptyView getEmptyLayout() {
        return emptyView;
    }

    @Override
    public void showErrorLayout() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.showErrorLayoutWithNetJudge(callBack);
    }

    @Override
    public void setLoadMoreViewVisible(boolean loadMoreViewVisible) {
        emptyView.setVisibility(loadMoreViewVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置正在加载的视图是否可见；
     * true： 可见
     * false：不可见
     */
    @Override
    public void setLoadingLayoutIsVisible(boolean loadingLayoutIsVisible) {
        emptyView.setVisibility(loadingLayoutIsVisible ? View.VISIBLE : View.GONE);
    }
}
