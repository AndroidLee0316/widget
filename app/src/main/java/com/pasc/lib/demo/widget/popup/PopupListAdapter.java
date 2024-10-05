package com.pasc.lib.demo.widget.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.SelectPopupWindow;
import com.pasc.lib.widget.popup.PascSelectPopupWindow;

import java.util.List;

public class PopupListAdapter extends  BaseQuickAdapter<CharSequence,BaseViewHolder > {

    Context mContext;
    private int singlePosition =-1;
    private int singleBeforePosition = -1;

    public PopupListAdapter(Context context,@Nullable List<CharSequence> data) {
        super(R.layout.popup_item, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CharSequence item) {

        TextView titleView = helper.getView(R.id.item_title);

        titleView.setText(item);

        if(singlePosition == helper.getLayoutPosition()){
            titleView.setTextColor(mContext.getResources().getColor(com.pasc.lib.widget.R.color.pasc_primary));
            helper.itemView.setSelected(true);
        } else {
            titleView.setTextColor(mContext.getResources().getColor(com.pasc.lib.widget.R.color.pasc_primary_text));
            helper.itemView.setSelected(false);
        }
        if(singleBeforePosition == helper.getLayoutPosition()){
            titleView.setTextColor(mContext.getResources().getColor(com.pasc.lib.widget.R.color.pasc_primary_text));
            helper.itemView.setSelected(false);
        }

    }
    public void setSelection(int beforePosition ,int selectedPosition){
        this.singlePosition = selectedPosition;
        notifyItemChanged(singlePosition);
        if(beforePosition != selectedPosition){
            this.singleBeforePosition = beforePosition;
            notifyItemChanged(singleBeforePosition);
        }

    }

}

