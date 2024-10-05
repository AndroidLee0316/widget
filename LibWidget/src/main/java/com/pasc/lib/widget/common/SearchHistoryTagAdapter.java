package com.pasc.lib.widget.common;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.flowlayout.FlowLayout;
import com.pasc.lib.widget.flowlayout.TagAdapter;

import java.util.List;

public class SearchHistoryTagAdapter<T extends IGetString> extends TagAdapter<T> {

    private Context context;

    public SearchHistoryTagAdapter(Context context, List<T> datas) {
        super(datas);
        this.context = context;

    }

    @Override
    public View getView(FlowLayout parent, int position, T item) {
        View tag = View.inflate(context, R.layout.item_search_tag, null);
        String formatStr = item.getString().replaceAll("\n", "");
        ((TextView) tag.findViewById(R.id.rtv_tag)).setText(formatStr);
        return tag;
    }
}
