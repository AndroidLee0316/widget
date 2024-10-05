package com.pasc.lib.demo.widget.seriesadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasc.lib.demo.R;
import com.pasc.lib.widget.seriesadapter.base.BaseHolder;
import com.pasc.lib.widget.seriesadapter.base.ItemModel;
import com.pasc.lib.widget.seriesadapter.base.SimpleWorker;

/**
 * Created by huanglihou519 on 2018/1/29.
 */

public class TitleModel extends ItemModel {
    private static final int TYPE = R.layout.item_series_title;

    public String title;

    public TitleModel() {
    }

    public TitleModel(String title) {
        this.title = title;
    }

    @Override
    public int layoutId() {
        return TYPE;
    }

    public static class TitleWorker extends SimpleWorker {

        @Override
        public BaseHolder create(ViewGroup parent, LayoutInflater inflater) {
            View view = inflater.inflate(TYPE, parent, false);
            return new TitleViewHolder(view);
        }

        @Override
        public void bind(BaseHolder viewHolder, ItemModel model) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) viewHolder;
            TitleModel titleModel = (TitleModel) model;
            titleViewHolder.textView.setText(titleModel.title);
        }

        @Override
        public int type() {
            return TYPE;
        }
    }

    public static class TitleViewHolder extends BaseHolder {

        TextView textView;

        public TitleViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.title_view);

        }
    }

}
