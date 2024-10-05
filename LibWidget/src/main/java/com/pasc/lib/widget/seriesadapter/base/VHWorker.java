package com.pasc.lib.widget.seriesadapter.base;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import static com.pasc.lib.widget.seriesadapter.utils.Preconditions.checkNotNull;

/**
 * Created by huanglihou519.
 */

public abstract class VHWorker {
    private ISeriesPresenter presenter;

    public abstract BaseHolder create(final ViewGroup parent, final LayoutInflater inflater);

    public abstract void bind(final BaseHolder viewHolder, final ItemModel model);

    public abstract int type();


    public void setPresenter(@NonNull ISeriesPresenter presenter) {
        checkNotNull(presenter, "Presenter should not be null");
        this.presenter = presenter;
    }


    @NonNull
    public ISeriesPresenter getPresenter() {
        return presenter;
    }
}
