package com.pasc.lib.widget.seriesadapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chendaixi947.
 */

public abstract class SimpleMainWorker<V extends BaseHolder, M extends ItemModel> extends VHWorker {
    @Override
    public V create(ViewGroup parent, LayoutInflater inflater) {
        View itemView = inflater.inflate(type(), parent, false);
        return createViewHolder(itemView);
    }

    @Override
    public void bind(BaseHolder viewHolder, ItemModel model) {
        bindViewHolderAndModel((V) viewHolder, (M) model);
    }

    public abstract V createViewHolder(View itemView);

    public abstract void bindViewHolderAndModel(V viewHolder, M model);
}
