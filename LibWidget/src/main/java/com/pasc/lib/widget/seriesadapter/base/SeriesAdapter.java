package com.pasc.lib.widget.seriesadapter.base;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.pasc.lib.widget.seriesadapter.utils.Preconditions.checkNotNull;

/**
 * Created by huanglihou519.
 */

public class SeriesAdapter extends RecyclerView.Adapter<BaseHolder> {
    private final List<ItemModel> itemModels;
    private final ISeriesPresenter presenter;
    private LayoutInflater inflater;

    public SeriesAdapter(@Nullable ISeriesPresenter presenter) {
        this(new ArrayList<ItemModel>(), presenter);
    }

    private SeriesAdapter(@Nullable List<ItemModel> itemModels,
                          @Nullable ISeriesPresenter presenter) {
        this.itemModels = checkNotNull(itemModels);
        this.presenter = checkNotNull(presenter);
        this.presenter.setViewModels(itemModels);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
        return presenter.createViewHolder(inflater, parent, viewType);
    }

    @Override
    public void onViewRecycled(BaseHolder holder) {
        super.onViewRecycled(holder);
        presenter.unBindViewHolder(holder);
    }

    @Override
    public long getItemId(int position) {
        return itemModels.get(position).id();
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        presenter.bindViewHolderAndModel(holder, itemModels.get(position));
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemModels.get(position).layoutId();
    }

    public List<ItemModel> getItemModels() {
        return itemModels;
    }

    @Deprecated
    public void updateItems(final List<ItemModel> newItems) {
        if (newItems == null) {
            return;
        }

        if (itemModels.size() == 0) {
            itemModels.addAll(newItems);
            notifyDataSetChanged();
            return;
        }

        if (newItems.size() == 0) {
            itemModels.clear();
            notifyDataSetChanged();
            return;
        }

        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(itemModels, newItems));
        itemModels.clear();
        itemModels.addAll(newItems);
        diffResult.dispatchUpdatesTo(this);
    }

    public static class DiffCallback extends DiffUtil.Callback {
        List<ItemModel> oldList;
        List<ItemModel> newList;

        public DiffCallback(List<ItemModel> oldList, List<ItemModel> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        Object getOldItem(int oldItemPosition) {
            return oldList.get(oldItemPosition);
        }

        Object getNewItem(int newItemPosition) {
            return newList.get(newItemPosition);
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldItem = getOldItem(oldItemPosition);
            Object newItem = getNewItem(newItemPosition);

            return !(oldItem == null || newItem == null) && oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldItem = getOldItem(oldItemPosition);
            Object newItem = getNewItem(newItemPosition);

            if (oldItem instanceof ContentsComparator) {
                return ((ContentsComparator) oldItem).areContentsEqual(newItem);
            } else {
                return areItemsTheSame(oldItemPosition, newItemPosition);
            }
        }
    }
}
