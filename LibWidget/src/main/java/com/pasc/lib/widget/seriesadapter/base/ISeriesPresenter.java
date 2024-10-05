package com.pasc.lib.widget.seriesadapter.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by huanglihou519.
 */

public interface ISeriesPresenter {
    BaseHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int type);

    void bindViewHolderAndModel(BaseHolder viewHolder, ItemModel model);

    void unBindViewHolder(final BaseHolder holder);

    void setViewModels(List<ItemModel> viewModels);

    List<ItemModel> getViewModels();
}

