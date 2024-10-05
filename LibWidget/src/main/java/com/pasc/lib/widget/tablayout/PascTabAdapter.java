
package com.pasc.lib.widget.tablayout;

import android.view.ViewGroup;


public class PascTabAdapter extends PascItemViewsAdapter<PascTab, PascTabView> implements PascTabView.Callback {
    private PascTabLayout mTabSegment;

    public PascTabAdapter(PascTabLayout tabSegment, ViewGroup parentView) {
        super(parentView);
        mTabSegment = tabSegment;
    }

    @Override
    protected PascTabView createView(ViewGroup parentView) {
        return new PascTabView(parentView.getContext());
    }

    @Override
    protected final void bind(PascTab item, PascTabView view, int position) {
        onBindTab(item, view, position);
        view.setCallback(this);
    }

    protected void onBindTab(PascTab item, PascTabView view, int position) {
        view.bind(item);
    }

    @Override
    public void onClick(PascTabView view) {
        int index = getViews().indexOf(view);
        mTabSegment.onClickTab(index);
    }

    @Override
    public void onDoubleClick(PascTabView view) {
        int index = getViews().indexOf(view);
        mTabSegment.onDoubleClick(index);
    }

    @Override
    public void onLongClick(PascTabView view) {
    }
}
