package com.pasc.lib.widget.loaderview;

import android.support.annotation.DrawableRes;
import android.view.View;

import com.pasc.lib.widget.ICallBack;


public interface IEmptyLayoutConfig {
    void showEmptyLayout();

    void showLoadingLayout();

    View getEmptyLayout();

    void showErrorLayout();

    void setCallBack(ICallBack callBack);

    void setCustomEmptyView(@DrawableRes int emptyIcon, String emptyContent);

    void setLoadMoreViewVisible(boolean loadMoreViewVisible);

    /**
     * 设置正在加载的视图是否可见；
     * true： 可见
     * false：不可见
     */
    void setLoadingLayoutIsVisible(boolean loadingLayoutIsVisible);
}
