package com.pasc.lib.widget.seriesadapter.base;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import static com.pasc.lib.widget.seriesadapter.utils.Preconditions.checkNotNull;

/**
 * Created by huanglihou519.
 */

public final class SeriesPresenter implements ISeriesPresenter {
    private final SparseArray<VHWorker> workers;
    private List<ItemModel> viewModels;


    private SeriesPresenter(SparseArray<VHWorker> workers) {
        this.workers = workers;
    }


    private SeriesPresenter(Builder builder) {
        this(builder.workers);
    }


    @Override
    public void setViewModels(List<ItemModel> models) {
        this.viewModels = models;
    }


    @Override
    public List<ItemModel> getViewModels() {
        return viewModels;
    }


    void setupWorker() {
        for (int i = 0; i < workers.size(); i++) {
            workers.valueAt(i).setPresenter(SeriesPresenter.this);
        }
    }


    @Override
    public BaseHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
        return getWorker(type).create(parent, inflater);
    }


    @Override
    public void bindViewHolderAndModel(BaseHolder viewHolder, ItemModel model) {
        VHWorker worker = getWorker(model.layoutId());
        if (worker != null) {
            worker.bind(viewHolder, model);
        }
    }

    private VHWorker lastWorkerLookup = null;

    private VHWorker getWorker(int type) {
        if (lastWorkerLookup != null && lastWorkerLookup.type() == type) {
            return lastWorkerLookup;
        }
        lastWorkerLookup = workers.get(type);
        return lastWorkerLookup;
    }


    @Override
    public void unBindViewHolder(final BaseHolder holder) {
        holder.unBind();
    }


    public static class Builder {
        private final SparseArray<VHWorker> workers;

        public Builder() {
            this.workers = new SparseArray<>();
        }


        public Builder addWorker(final VHWorker worker) {
            checkNotNull(worker, "worker must not be null!!");
            if (this.workers.get(worker.type()) != null) {
                throw new IllegalArgumentException("worker type has exits!!");
            }
            this.workers.put(worker.type(), worker);
            return this;
        }


        public SeriesPresenter build() {
            SeriesPresenter presenter = new SeriesPresenter(this);
            presenter.setupWorker();
            return presenter;
        }
    }
}
