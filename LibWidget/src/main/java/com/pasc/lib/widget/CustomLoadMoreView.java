package com.pasc.lib.widget;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by zhangcan603 on 2018/3/26.
 */

public class CustomLoadMoreView extends LoadMoreView {
    private boolean loadEndViewVisible;

    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more;
    }

    /**
     * 如果返回true，数据全部加载完毕后会隐藏加载更多
     * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
     */
    @Override
    public boolean isLoadEndGone() {
        return true;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    /**
     * isLoadEndGone()为true，可以返回0
     * isLoadEndGone()为false，不能返回0
     */
    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

    @Override
    public void convert(BaseViewHolder holder) {
        int loadEndViewId = getLoadEndViewId();
        if (loadEndViewId != 0) {
            View loadEndView = holder.getView(loadEndViewId);
            if (loadEndView != null) {
                loadEndView.setAlpha(loadEndViewVisible ? 0 : 1);
            }
        }
        super.convert(holder);
    }

    public void setLoadEndViewVisible(boolean loadEndViewVisible) {
        this.loadEndViewVisible = loadEndViewVisible;
    }
}
